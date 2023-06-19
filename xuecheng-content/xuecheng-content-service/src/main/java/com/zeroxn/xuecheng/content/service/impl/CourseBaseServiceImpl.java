package com.zeroxn.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.base.utils.CommonUtils;
import com.zeroxn.xuecheng.content.mapper.CourseBaseMapper;
import com.zeroxn.xuecheng.content.model.DTO.CourseDTO;
import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.QueryCourseParamsDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;
import com.zeroxn.xuecheng.content.model.pojo.CourseCategory;
import com.zeroxn.xuecheng.content.model.pojo.CourseMarket;
import com.zeroxn.xuecheng.content.service.CourseBaseService;
import com.zeroxn.xuecheng.content.service.CourseCategoryService;
import com.zeroxn.xuecheng.content.service.CourseMarketService;
import com.zeroxn.xuecheng.content.service.async.CourseAsyncTask;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午12:31
 * @Description:
 */
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {
    private final CourseBaseMapper baseMapper;
    private final CourseMarketService marketService;
    private final CourseCategoryService categoryService;
    public CourseBaseServiceImpl(CourseBaseMapper baseMapper, CourseMarketService marketService,
                                 CourseCategoryService categoryService){
        this.baseMapper = baseMapper;
        this.marketService = marketService;
        this.categoryService = categoryService;
    }
    @Override
    public PageResult<CourseBase> queryCourseBaseListByPage(PageParams params, QueryCourseParamsDTO courseParamsDTO) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        if(courseParamsDTO != null){
            queryWrapper.like(StringUtils.isNotEmpty(courseParamsDTO.getCourseName()), CourseBase::getName,
                    courseParamsDTO.getCourseName());
            queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDTO.getAuditStatus()), CourseBase::getAuditStatus,
                    courseParamsDTO.getAuditStatus());
            queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDTO.getPublishStatus()),
                    CourseBase::getStatus, courseParamsDTO.getPublishStatus());
        }
        Page<CourseBase> basePage = new Page<>(params.getPageNo(), params.getPageSize());
        Page<CourseBase> pageResult = baseMapper.selectPage(basePage, queryWrapper);
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal(), params.getPageNo(),
                params.getPageSize());
    }

    /**
     * 添加课程 先写入课程基础内容 再写入课程营销信息
     * @param companyId 机构Id
     * @param courseDTO 添加课程所需的参数
     * @return 返回当前课程的详细信息 包含营销信息
     */
    @Transactional
    @Override
    public CourseBaseInfoDTO addCourseBase(Long companyId, CourseDTO courseDTO) {
        if(CommonUtils.checkObjectFieldIsEmpty(courseDTO, "name", "users", "mt", "st", "grade", "charge")){
            throw new CustomException("添加课程参数错误");
        }
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(courseDTO, courseBase);
        courseBase.setCompanyId(companyId);
        courseBase.setCreateDate(LocalDateTime.now());
        courseBase.setAuditStatus("202002");
        courseBase.setStatus("203001");
        int insert = baseMapper.insert(courseBase);
        if (insert < 1){
            throw new CustomException("课程添加失败");
        }
        CourseMarket market = new CourseMarket();
        BeanUtils.copyProperties(courseDTO, market);
        Long courseId = courseBase.getId();
        market.setId(courseId);
        boolean bl = saveCourseMarket(market);
        if(!bl){
            throw new CustomException("课程营销信息添加失败");
        }
        return queryCourseBaseInfoById(courseId);
    }

    /**
     * 保存课程营销信息 保存之前先查找 如果存在那么更新 不存在则添加
     * @param market 课程营销参数
     * @return 添加成功或失败
     */
    private boolean saveCourseMarket(CourseMarket market){
        String charge = market.getCharge();
        if(StringUtils.isEmpty(charge)){
           throw new CustomException("收费规则为空");
        }
        if(charge.equals("201001")){
            if(market.getPrice() == null || market.getPrice() <= 0){
                throw new CustomException("收费课程价格信息错误");
            }
        }
        CourseMarket findMarket = marketService.getById(market.getId());
        if(findMarket == null){
            return marketService.save(market);
        }else{
            BeanUtils.copyProperties(market, findMarket);
            return marketService.updateById(findMarket);
        }
    }

    /**
     * 查询课程详细信息 包含营销信息
     * @param courseId 课程id
     * @return 课程详细信息
     */
    @Override
    public CourseBaseInfoDTO queryCourseBaseInfoById(Long courseId){
        // 查询课程
        CourseBase findCourseBase = baseMapper.selectById(courseId);
        if(findCourseBase == null){
            return null;
        }
        CourseBaseInfoDTO courseBaseInfoDTO = new CourseBaseInfoDTO();
        BeanUtils.copyProperties(findCourseBase, courseBaseInfoDTO);
        // 查询课程营销信息
        CourseMarket findCourseMarket = marketService.getById(courseId);
        if(findCourseMarket != null){
            BeanUtils.copyProperties(findCourseMarket, courseBaseInfoDTO);
        }
        // 查询分类名称
        String mtName = categoryService.getById(findCourseBase.getMt()).getName();
        String stName = categoryService.getById(findCourseBase.getSt()).getName();
        courseBaseInfoDTO.setMtName(mtName);
        courseBaseInfoDTO.setStName(stName);
        return courseBaseInfoDTO;
    }
    @Transactional
    @Override
    public CourseBaseInfoDTO updateCourseBase(Long companyId, CourseDTO courseDTO) {
        CourseBase courseBase = baseMapper.selectById(courseDTO.getId());
        if (courseBase == null){
            throw new CustomException("查询参数为空");
        }
        if(!companyId.equals(courseBase.getCompanyId())){
            throw new CustomException("机构ID错误");
        }
        BeanUtils.copyProperties(courseDTO, courseBase);
        courseBase.setChangeDate(LocalDateTime.now());
        // 更新课程信息
        baseMapper.updateById(courseBase);
        CourseMarket courseMarket = new CourseMarket();
        // 更新课程营销信息
        BeanUtils.copyProperties(courseDTO, courseMarket);
        courseMarket.setId(courseBase.getId());
        boolean bl = saveCourseMarket(courseMarket);
        if(!bl){
            throw new CustomException("课程营销信息更新失败");
        }
        return queryCourseBaseInfoById(courseBase.getId());
    }
}
