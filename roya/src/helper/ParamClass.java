/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import oracle.jdbc.OracleTypes;

/**
 *
 * @author IbrahimDarwiesh
 */
public class ParamClass {

    public enum ParamType {

        IN,
        OUT,
        INOUT
    }

    private int paramIndex;
    private ParamType paramType;
    private Object paraValue;
    private int paraOutType; // C for cusror == V for varchar2

    public int getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    public ParamType getParamType() {
        return paramType;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    public Object getParaValue() {
        return paraValue;
    }

    public void setParaValue(Object paraValue) {
        this.paraValue = paraValue;
    }

    public ParamClass() {
    }

    public ParamClass(int paramIndex, ParamType paramType, Object paraValue) {
        this.paramIndex = paramIndex;
        this.paramType = paramType;
        this.paraValue = paraValue;
    }

    public ParamClass(int paramIndex, ParamType paramType, int paraOutType) {
        this.paramIndex = paramIndex;
        this.paramType = paramType;
        this.paraOutType = paraOutType;
    }

    public ParamClass(int paramIndex, Object paraValue) {
        this.paramIndex = paramIndex;
        this.paraValue = paraValue;
        this.paramType = paramType.IN;
    }

    public int getParaOutType() {
        return paraOutType;
    }

    public void setParaOutType(int paraOutType) {
        this.paraOutType = paraOutType;
    }

}
