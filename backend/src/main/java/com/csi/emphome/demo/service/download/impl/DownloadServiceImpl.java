package com.csi.emphome.demo.service.download.impl;

import com.csi.emphome.demo.utils.config.FileProperties;
import com.csi.emphome.demo.domain.download.DownloadItem;
import com.csi.emphome.demo.repository.download.DownloadRepository;
import com.csi.emphome.demo.service.download.DownloadService;
import com.csi.emphome.demo.service.download.dto.DownloadListQuery;
import com.csi.emphome.demo.service.download.dto.DownloadSearchData;
import com.csi.emphome.demo.service.download.dto.DownloadTemp;
import com.csi.emphome.demo.utils.other.FileUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * 下载中心server层实现
 *
 * @author hhc
 * @date 2021/06/30
 */
@Service
public class DownloadServiceImpl implements DownloadService {
    /**
     * 数据库jpa操作
     */
    private final DownloadRepository downloadRepository;

    /**
     * 文件操作参数
     */
    private final FileProperties properties;

    public DownloadServiceImpl(DownloadRepository downloadRepository, FileProperties properties) {
        this.downloadRepository = downloadRepository;
        this.properties = properties;
    }

    /**
     * 拉取数据库表
     *
     * @param data DTO：DownloadListQuery
     * @return response
     */
    @Override
    public HashMap<String, Object> fetchListFunc(DownloadListQuery data) {
        HashMap<String, Object> responseData = new HashMap<>();
        List<DownloadItem> list = downloadRepository.findAll(PageRequest.of(data.getPage()-1, data.getLimit())).toList();
        responseData.put("total", downloadRepository.count());
        responseData.put("items",list);

        HashMap<String, Object> response = new HashMap<>();
        response.put("code",20000);
        response.put("data",responseData);
        return response;
    }

    /**
     * 分页函数实现
     *
     * @param list 列表
     * @param pageNum 当前页数>0
     * @param pageSize 总页数
     * @return 被分页处理后的表
     */
    public static List<DownloadItem> splicePage(List<DownloadItem> list, Integer pageNum, Integer pageSize) {
        if(list == null){
            return null;
        }
        if(list.size() == 0){
            return null;
        }

        Integer count = list.size(); //记录总数
        Integer pageCount = 0; //页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; //开始索引
        int toIndex = 0; //结束索引

        if(pageNum > pageCount){
            pageNum = pageCount;
        }
        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        return list.subList(fromIndex, toIndex);
    }

    /**
     * 分页查询
     *
     * @param data DTO：DownloadSearchData
     * @return response
     */
    @Override
    public HashMap<String, Object> fetchListItemFunc(DownloadSearchData data) {
        HashMap<String, Object> responseData = new HashMap<>();

        if(data.getSearch().equals("")){
            return fetchListFunc(data.getListQuery());
        }
        List<DownloadItem> listAll;
        switch (data.getSelect()) {
            case "2": {
                listAll = downloadRepository.findAllByCreateBy(data.getSearch());
                break;
            }
            case "3": {
                listAll = downloadRepository.findAllByDetail(data.getSearch());
                break;
            }
            default: {
                listAll = downloadRepository.findAllByName(data.getSearch());
                break;
            }
        }
        List<DownloadItem> list;
        if(listAll.size()>0){
            list = splicePage(listAll, data.getListQuery().getPage(), data.getListQuery().getLimit());
        }else {
            list = listAll;
        }

        responseData.put("total",listAll.size());
        responseData.put("items",list);

        HashMap<String, Object> response = new HashMap<>();
        response.put("code",20000);
        response.put("data",responseData);
        return response;
    }

    /**
     * 新建条目
     *
     * @param data DTO：DownloadTemp
     * @return response
     */
    @Override
    public HashMap<String, Object> createListItemFunc(DownloadTemp data) {
        int resCode = 20001;
        String resData = "failed";

        DownloadItem tag_item = downloadRepository.findByStorageId(data.getStorageId());
        if (tag_item == null){
            DownloadItem temp_item = new DownloadItem();
            temp_item.setStorageId(data.getStorageId());
            temp_item.setRealName(data.getRealName());
            temp_item.setName(data.getName());
            temp_item.setCreateTime(data.getCreateTimeByTS());
            temp_item.setCreateBy(data.getCreateBy());
            temp_item.setDetail(data.getDetail());
            downloadRepository.save(temp_item);
            resCode = 20000;
            resData = "success";
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("code",resCode);
        response.put("data",resData);
        return response;
    }

    /**
     * 修改条目
     *
     * @param data DTO：DownloadTemp
     * @return response
     */
    @Override
    public HashMap<String, Object> updateListItemFunc(DownloadTemp data) {
        int resCode = 20001;
        String resData = "failed";

        DownloadItem tag_item = downloadRepository.findByStorageId(data.getStorageId());
        if (tag_item != null){
            tag_item.setStorageId(data.getStorageId());
            tag_item.setRealName(data.getRealName());
            tag_item.setName(data.getName());
            tag_item.setCreateTime(data.getCreateTimeByTS());
            tag_item.setCreateBy(data.getCreateBy());
            tag_item.setDetail(data.getDetail());
            downloadRepository.save(tag_item);
            resCode = 20000;
            resData = "success";
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("code",resCode);
        response.put("data",resData);
        return response;
    }

    /**
     * 删除多个条目
     *
     * @param data DTO：List<DownloadTemp>
     * @return response
     */
    @Override
    public HashMap<String, Object> deleteListFunc(List<DownloadTemp> data) {
        HashMap<String, Object> response = new HashMap<>();

        for (DownloadTemp datum : data) {
            DownloadItem tag_item = downloadRepository.findByStorageId(datum.getStorageId());
            if (tag_item != null) {
                FileUtil.del(tag_item.getPath());
                downloadRepository.delete(tag_item);
            } else {
                response.put("code", 20001);
                response.put("data", "failed");
                return response;
            }
        }
        response.put("code", 20000);
        response.put("data", "success");
        return response;
    }

    /**
     * 上传文件
     *
     * @param data DTO：DownloadTemp
     * @param multipartFile 文件数据
     * @return response
     */
    @Override
    public HashMap<String, Object> uploadFileFunc(DownloadTemp data, MultipartFile multipartFile) {
        HashMap<String, Object> response = new HashMap<>();

        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath() + type +  File.separator);

        DownloadItem tag_item = downloadRepository.findByRealName(file.getName());
        if (tag_item == null) {
            DownloadItem temp_item = new DownloadItem();
            temp_item.setStorageId(data.getStorageId());
            temp_item.setRealName(file.getName());
            temp_item.setName(data.getName());
            temp_item.setSuffix(suffix);
            temp_item.setPath(file.getPath());
            temp_item.setType(type);
            temp_item.setSize(FileUtil.getSize(multipartFile.getSize()));
            temp_item.setCreateBy(data.getCreateBy());
            temp_item.setCreateTime(data.getCreateTimeByTS());
            temp_item.setDetail(data.getDetail());
            System.out.println(temp_item);
            downloadRepository.save(temp_item);
            response.put("code",20000);
            response.put("data",temp_item);
        }else {
            response.put("code",20001);
            response.put("data","failed");
        }
        return response;
    }
}
