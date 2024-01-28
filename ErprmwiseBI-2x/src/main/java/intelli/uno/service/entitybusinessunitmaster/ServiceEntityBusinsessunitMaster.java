package intelli.uno.service.entitybusinessunitmaster;

import java.util.List;

import intelli.uno.entity.EntityBusinessunitMaster;

public interface ServiceEntityBusinsessunitMaster {
   public abstract List<EntityBusinessunitMaster> returnListOfBuForSpecificUser(List<Long> l_longBuIdList,String deleteflagBum);
}
