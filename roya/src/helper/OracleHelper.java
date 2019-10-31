/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import static helper.ParamClass.ParamType.IN;
import static helper.ParamClass.ParamType.OUT;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bkeryah.dao.DataBaseConnectionClass;

import oracle.jdbc.OracleTypes;

;

/**
 *
 * @author IbrahimDarwiesh
 */
public class OracleHelper {

    public List<Object[]> getData(String ProcedureName, List<ParamClass> paraList, int returnIndex) {
        List<Object> resultList = new ArrayList<Object>();
        List<Object[]> records = new LinkedList<Object[]>();
        String P = "";
        for (int i = 1; i <= 4; i++) {
            P = P + ",?";
        }
        P = P.substring(1);
        try {
            Connection connection = DataBaseConnectionClass.getConnection();
            String SQl = "{call " + ProcedureName + "(" + P + ")}";
            CallableStatement callableStatement = connection.prepareCall(SQl);
            for (ParamClass object : paraList) {
                ParamClass pc = (ParamClass) object;
                if (pc.getParamType() == IN) {
                    callableStatement.setString(pc.getParamIndex(), pc.getParaValue().toString());
                } else if (pc.getParamType() == OUT) {
                    callableStatement.registerOutParameter(pc.getParamIndex(), pc.getParaOutType());
                }
            }
            callableStatement.executeUpdate();
            ResultSet resultSet = (ResultSet) callableStatement.getObject(returnIndex);

            int cols = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Object[] arr = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    arr[i] = resultSet.getObject(i + 1);
                }
                records.add(arr);
            }
            System.err.println("size : " + records.size());
            connection.close();
        } catch (Exception e) {
        }

        return records;

    }

}
