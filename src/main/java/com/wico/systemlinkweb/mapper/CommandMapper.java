package com.wico.systemlinkweb.mapper;

import com.wico.systemlinkweb.domain.GasCommand;
import com.wico.systemlinkweb.domain.command.Command;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author XuCheng
 * @Date 2020/4/29 15:33
 */
@Mapper
public interface CommandMapper {

    Command getCommandAttribute(String deviceIdKey);

    void insertGasCommand(GasCommand gasCommand);

    void insertEndPacket(GasCommand gasCommand);

    List<GasCommand> selectGasCommand(String s);

    void updateGasCommand(GasCommand gasCommand);

    List<GasCommand> selectGasEndPacket(String s);

    void updateEndPacket(GasCommand gasCommand);

    Command getAttribute(String deviceIdKey);
}
