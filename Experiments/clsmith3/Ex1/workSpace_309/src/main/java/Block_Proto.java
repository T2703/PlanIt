public class Block_Proto {
    String blockName = "";
    int blockID = 0;        //format ?
    String blockDescription ="";
    String location = "";  //Ext to google maps
    int dateID = 0;        //ID# of connected day
    double startTime = 0;  //Military time, 6:45pm = 18.45
    double endTime = 0;    //default is startTime +1.0
    boolean recurring = false; //if true, implement extension for further options
    boolean reminder = false;  //if true, implement extension for further options+
    //TODO list of shared events and attached days
    //TODO 'Event' as ext of Block

    public void editBlock(){

    }
}
