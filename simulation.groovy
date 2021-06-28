import org.arl.fjage.*
import org.arl.unet.sim.channels.*
import org.arl.unet.*

channel.model=ProtocolChannelModel;
println '''
3-node network
--------------
'''
channel.communicationRange=534.m
platform = RealTimePlatform

simulate {
  
  node 'B', address:107,  location: [5.m,  500.m, -15.m], web:8081,api: 1101, shell: true,
  stack: "$home/etc/setup2"
  
  println 'node n1 started'

  
  node 'n1', address: 101, location: [300.m, 725.m, -15.m], web:8082,api: 1102, shell: 5101, 
  stack: "$home/etc/setup3"
  println 'node n2 started'
 
  node 'n2', address: 102, location: [200.m, 500.m, -15.m], web:8083,api: 1103, shell: 5102, 
  stack: "$home/etc/setup3"
  println 'node n3 started'

  node 'n3', address: 103, location: [ 500.m, 300.m, -15.m], web:8084,api: 1104, shell: 5103, 
  stack: "$home/etc/setup3"
  
  node 'n6', address: 106, location: [ 600.m, 500.m, -15.m], web:8087,api: 1107, shell: 5106, 
  stack: "$home/etc/setup3"


}
