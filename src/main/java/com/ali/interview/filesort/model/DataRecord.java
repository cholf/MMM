package com.ali.interview.filesort.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/9/14
 * Time  : 下午14:09
 * 类描述 : 文件存储数据行
 */
public class DataRecord {
    private String id;
    /**
     * 分组
     */
    private String groupId;

    /**
     * 指标
     */
    private float quota;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public float getQuota() {
        return quota;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }
}
