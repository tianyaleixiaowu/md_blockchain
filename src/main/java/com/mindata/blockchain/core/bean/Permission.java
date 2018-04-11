package com.mindata.blockchain.core.bean;

/**
 * 权限，主要存储各member对表的权限信息，如不可见、只能ADD，可以UPDATE、DELETE等等组合
 * @author wuweifeng wrote on 2018/3/5.
 */
public class Permission {
    /**
     * 哪张表
     */
    private String tableName;
    /**
     * 操作权限，见PermissionType类
     */
    private byte permissionType;
    /**
     * 公钥（账户的概念，能具体到某个member，为*则代表所有节点，不具体指定某个）
     */
    private String publicKey;
    /**
     * 该权限是归属于哪个group的。节点只需要获取自己group的权限信息，不需要知道别的group的
     */
    private String groupId;

    @Override
    public String toString() {
        return "Permission{" +
                "tableName='" + tableName + '\'' +
                ", permissionType=" + permissionType +
                ", publicKey='" + publicKey + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public byte getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(byte permissionType) {
        this.permissionType = permissionType;
    }
}
