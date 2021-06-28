
import org.arl.fjage.*
import org.arl.fjage.Message
import org.arl.fjage.RealTimePlatform
import org.arl.unet.phy.*
import org.arl.unet.mac.*
import org.arl.unet.*
import org.arl.unet.net.*
import org.arl.unet.PDU
import org.arl.unet.net.Router
import org.arl.unet.nodeinfo.NodeInfo
import org.arl.unet.localization.*
import org.arl.unet.localization.RangeNtf.*
import org.arl.unet.localization.Ranging.*
import org.arl.unet.localization.RangeReq
import org.arl.unet.net.RouteDiscoveryReq
import java.nio.ByteOrder
import org.arl.fjage.Behavior

class anchor_agent extends UnetAgent {
    def addr;
    def nme;
    def phy;
    def nodeInfo;
    def loc;
    def router;

  private final static PDU format = PDU.withFormat
    {
        uint16('addres');
        int16('loca');
        int16('loca1');
        int16('loca2');


    }
  
  void startup() 
  {
    phy = agentForService Services.PHYSICAL;
    subscribe topic(phy);
    
    router = agentForService Services.ROUTING;
    subscribe topic(router);
    
    
    nodeInfo = agentForService Services.NODE_INFO;
    
  }
  
  void processMessage(Message msg) 
  {
      
    addr = nodeInfo.address;
    nme  = nodeInfo.nodeName;
    loc  = nodeInfo.location;
      
    def datapacket = format.encode(addres: addr, loca: loc[0], loca1: loc[1], loca2: loc[2]);
    if (addr==103)
    {
        datapacket = format.encode(addres: addr, loca: loc[0]-495.0, loca1: loc[1]+150.0, loca2: loc[2]);
        nodeInfo.canForward=false;
        
    }
    
    if(msg instanceof DatagramNtf && msg.protocol==Protocol.MAC)
    { 
       def n=rndint(4);
      def k=rndint(5);

      def delay=(n+1)*(k+1);
      
       
       
       println "Broadcast packet received at node "+nme+"location is"+loc[0];
       println "Node "+nme+" will respond after "+ delay +" seconds"
       println ' '
       
       
       
       add new WakerBehavior(delay*1000,{
       phy << new TxFrameReq(to: msg.from, 
                             protocol: Protocol.MAC, 
                             data: datapacket)  
       })
       
    }
    
  }
}