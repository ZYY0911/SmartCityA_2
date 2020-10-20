package com.example.smartcitya_2.bean;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 20:12
 */
public class NewComment {

    /**
     * num : 1
     * commit : 祝福祖国
     * commitTime : 2020-10-01 11:24:01
     * reviewer : abc
     */

    private int num;
    private String commit;
    private String commitTime;
    private String reviewer;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
}
