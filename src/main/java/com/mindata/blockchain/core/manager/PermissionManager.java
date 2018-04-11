package com.mindata.blockchain.core.manager;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.Instruction;
import com.mindata.blockchain.common.PermissionType;
import com.mindata.blockchain.core.bean.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 对Permission信息的存储和使用
 *
 * @author wuweifeng wrote on 2018/4/10.
 */
@Service
public class PermissionManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 将权限信息常驻内存
     */
    public static final Map<String, List<Permission>> PERMISSION_MAP = new HashMap<>();

    /**
     * 校验block内的所有指令的权限是否合法
     * @param block 区块
     * @return 合法
     */
    public boolean checkPermission(Block block) {
        List<Instruction> instructions = block.getBlockBody().getInstructions();
        return checkPermission(instructions);
    }

    public boolean checkPermission(List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            String publicKey = instruction.getPublicKey();
            String tableName = instruction.getTable();
            byte operation = instruction.getOperation();
            //TODO 这块要优化，循环次数太多，需要精简
            if (!checkOperation(publicKey, tableName, operation)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 校验某用户对某表的某个操作是否有权限
     *
     * @param publicKey
     *         公钥
     * @param tableName
     *         表名
     * @param operation
     *         操作
     * @return 有权限true
     */
    private boolean checkOperation(String publicKey, String tableName, byte operation) {
        List<Permission> permissionList = PERMISSION_MAP.get(tableName);

        Set<Byte> userPermissionSet = new HashSet<>();
        for (Permission permission : permissionList) {
            //如果是不限用户的情况，取到该表的所有公开的权限
            if ("*".equals(permission.getPublicKey())) {
                userPermissionSet.add(permission.getPermissionType());
            } else {
                //找到该publicKey的所有权限
                if (publicKey.equals(permission.getPublicKey())) {
                    userPermissionSet.add(permission.getPermissionType());
                }
            }
        }

        //判断该用户的权限是否包含operation
        return userPermissionSet.contains(PermissionType.OWNER)
                || userPermissionSet.contains(PermissionType.ALL)
                || userPermissionSet.contains(operation);
    }


    /**
     * 保存权限信息，static常驻内存，按table划分到map里
     *
     * @param permissions
     *         permissions
     */
    public void savePermissionList(List<Permission> permissions) {
        PERMISSION_MAP.clear();
        for (Permission permission : permissions) {
            String key = permission.getTableName();
            if (!PERMISSION_MAP.containsKey(key)) {
                PERMISSION_MAP.put(key, new ArrayList<>());
            }
            PERMISSION_MAP.get(key).add(permission);
        }
        logger.info("所有的权限信息：" + PERMISSION_MAP);
    }

}
