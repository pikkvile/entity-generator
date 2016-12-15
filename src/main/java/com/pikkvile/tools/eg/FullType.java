package com.pikkvile.tools.eg;

public class FullType {

    private final String pkgName;
    private final String selfName;

    public FullType(String pkgName, String selfName) {
        this.pkgName = pkgName;
        this.selfName = selfName;
    }

    public String getPackageName() {
        return pkgName;
    }

    public boolean hasPackage() {
        return !pkgName.isEmpty();
    }

    public String getSelfName() {
        return selfName;
    }
}
