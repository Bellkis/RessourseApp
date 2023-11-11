package org.groupe2.iactic5;

import org.groupe2.iactic5.room.entities.Room;
import org.groupe2.iactic5.room.repositories.RoomFileRepository;
import org.groupe2.iactic5.room.services.RoomServices;
import org.groupe2.iactic5.room.services.RoomServicesInterface;


public class App 
{
    public static void main( String[] args )
    {
        RoomServicesInterface roomServicesInterface = new RoomServices(new RoomFileRepository());

        //Créer une nouvelle chambre
        Room room = new Room();
        room.setId(2);
        room.setName("Chambre test 2");
        room.setCapacity(3);

        roomServicesInterface.createRoom(room);

        roomServicesInterface.deleteRoom(1);

    }
}
