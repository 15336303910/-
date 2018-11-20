package interfaces.pdainterface.generator.service.impl;

import java.util.List;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.generator.service.IPDAMachineRoomService;
import manage.generator.pojo.MachineRoomInfoBean;

public class PDAMachineRoomServiceImpl extends DataBase implements IPDAMachineRoomService{
	private static final String INSERT_MACHINEROOM = "pdagenerator.insertMachineRoom";
	private static final String GET_MACHINEROOM = "pdagenerator.getMachineRoom";
	private static final String UPDATE_MACHINEROOM = "pdagenerator.updateMachineRoom";
	private static final String DELETE_MACHINEROOM = "pdagenerator.deleteMachineRoom";
	
	@Override
	public int insertMachineRoom(MachineRoomInfoBean machineRoom) throws DataAccessException {
	    return ((Integer)insert(INSERT_MACHINEROOM, machineRoom)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MachineRoomInfoBean> getMachineRoom(MachineRoomInfoBean machineRoom) throws DataAccessException {
		if(TextUtil.isNotNull(machineRoom.getZh_label())){
			String roomName = machineRoom.getZh_label().trim();
			if(roomName.contains(" ")){
				roomName= roomName.replaceAll(" +", "%");
			}
			machineRoom.setZh_label("%"+roomName+"%");
		}
		return getObjects(GET_MACHINEROOM, machineRoom);
	}

	@Override
	public int updateMachineRoom(MachineRoomInfoBean machineRoom) throws DataAccessException {
		return update(UPDATE_MACHINEROOM, machineRoom);
	}

	@Override
	public int deleteMachineRoom(MachineRoomInfoBean machineRoom) throws DataAccessException {
		return update(DELETE_MACHINEROOM, machineRoom);
	}

}
