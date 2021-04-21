package org.rosychao.springframework.aop.config;

/**
 * 用于保存Aop切面配置
 */
public class AopConfig {

    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectAround;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
    private boolean aspectJAutoProxy;

    public String getPointCut() {
        // 为了适应正则表达式，特殊字符需要做处理
        return pointCut.replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }

    public String getAspectBefore() {
        return aspectBefore;
    }

    public void setAspectBefore(String aspectBefore) {
        this.aspectBefore = aspectBefore;
    }

    public String getAspectAfter() {
        return aspectAfter;
    }

    public void setAspectAfter(String aspectAfter) {
        this.aspectAfter = aspectAfter;
    }

    public String getAspectAround() {
        return aspectAround;
    }

    public void setAspectAround(String aspectAround) {
        this.aspectAround = aspectAround;
    }

    public String getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(String aspectClass) {
        this.aspectClass = aspectClass;
    }

    public String getAspectAfterThrow() {
        return aspectAfterThrow;
    }

    public void setAspectAfterThrow(String aspectAfterThrow) {
        this.aspectAfterThrow = aspectAfterThrow;
    }

    public String getAspectAfterThrowingName() {
        return aspectAfterThrowingName;
    }

    public void setAspectAfterThrowingName(String aspectAfterThrowingName) {
        this.aspectAfterThrowingName = aspectAfterThrowingName;
    }

    public boolean isAspectJAutoProxy() {
        return aspectJAutoProxy;
    }

    public void setAspectJAutoProxy(boolean aspectJAutoProxy) {
        this.aspectJAutoProxy = aspectJAutoProxy;
    }
}
