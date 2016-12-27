package com.pikkvile.tools.eg;

public class FullType {

    private final String pkgName;
    private final String selfName;

    public FullType(String pkgName, String selfName) {
        this.pkgName = pkgName;
        this.selfName = selfName;
    }

    @Override
    public String toString() {
        return pkgName.isEmpty() ? selfName : pkgName + "." + selfName;
    }
}
