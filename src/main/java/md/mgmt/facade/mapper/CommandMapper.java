package md.mgmt.facade.mapper;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.constant.OpsTypeEnum;
import md.mgmt.base.md.MdAttr;
import md.mgmt.base.ops.ReqDto;
import md.mgmt.base.ops.RespDto;
import md.mgmt.facade.req.PutMdAttrDto;
import md.mgmt.service.GetMdAttrService;
import md.mgmt.service.PutMdAttrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mr-yang on 15-12-22.
 */
@Component
public class CommandMapper {
    private Logger logger = LoggerFactory.getLogger(CommandMapper.class);

    @Autowired
    private PutMdAttrService putMdAttrService;

    @Autowired
    private GetMdAttrService getMdAttrService;

    /**
     * 按客户端命令选择对应的服务
     */
    public String selectService(String cmd) {
        ReqDto reqDto = JSON.parseObject(cmd, ReqDto.class);
        String opsContent = reqDto.getOpsContent();
        if (opsContent == null) {
            return getRespStr(false, "参数错误", null);
        }
        if (reqDto.getOpsType() == OpsTypeEnum.CREATE_FILE.getCode() ||
                reqDto.getOpsType() == OpsTypeEnum.CREATE_DIR.getCode()) {
            return putMdAttr(opsContent);
        } else if (reqDto.getOpsType() == OpsTypeEnum.FIND_FILE.getCode()) {
            return getFileMdAttr(opsContent);
        } else if (reqDto.getOpsType() == OpsTypeEnum.LIST_DIR.getCode()) {
            return getDirMdAttrList(opsContent);
        }
        return getRespStr(false, "参数错误", null);
    }

    private String getFileMdAttr(String opsContent) {
        MdAttr mdAttr = getMdAttrService.getFileMdAttr(opsContent);
        if (mdAttr == null) {
            return getRespStr(false, "获取文件元数据失败", null);
        }
        return getRespStr(true, "获取文件元数据成功", mdAttr);
    }

    private String getDirMdAttrList(String opsContent) {
        List<MdAttr> mdAttrs = getMdAttrService.getDirMdAttrList(opsContent);
        if (mdAttrs == null) {
            return getRespStr(false, "列表目录元数据失败", null);
        }
        return getRespStr(true, "列表目录元数据成功", mdAttrs);
    }

    private String putMdAttr(String opsContent) {
        PutMdAttrDto putMdAttrDto = JSON.parseObject(opsContent, PutMdAttrDto.class);
        boolean result = putMdAttrService.putMdAttr(putMdAttrDto);
        if (!result) {
            return getRespStr(false, "创建元数据失败", null);
        }
        return getRespStr(true, "创建元数据成功", null);
    }

    private String getRespStr(boolean success, String msg, Object objStr) {
        RespDto respDto = new RespDto();
        respDto.setSuccess(success);
        respDto.setMsg(msg);
        respDto.setObjStr(JSON.toJSONString(objStr));
        return JSON.toJSONString(respDto);
    }

}
