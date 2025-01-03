package org.example.fraud.mapper;

import org.example.fraud.model.Account;
import org.apache.ibatis.annotations.*;


@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM account WHERE id = #{id}")
    @Results({@Result(property = "id", column = "id"), @Result(property = "suspiciousStatus", column = "suspicious_status")})
    Account findById(String id);

}
