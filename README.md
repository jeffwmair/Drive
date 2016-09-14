# Drive
3D Driving Game/Simulation in Java

Uses [J3DFW](https://github.com/jeffwmair/j3dfw), which is a really just a wrapper around the JOGL framework to add some additional helpful abstraction.
## Getting Started
```shell
$ git clone git@github.com:jeffwmair/Drive.git
$ cd Drive
$ mvn package
$ cd target/
$ java -jar Drive-1.0.jar
```
## Operation
### Car Control
* left mouse - hold to accelerate
* right mouse - hold to brake
* mouse pointer left/right movement to steer
* 1 - open/close the hood

### Camera Control
* mouse scroll wheel rotates the camera around the vehicle
* shift + mouse scroll adjusts the camera vertical angle
* CMD (mac!) + mouse scroll zooms the camera in and out

### Pictures
Quick screenshots of the current progress.
![Alt text](/documentation/img/CutlassRotate.gif)
![Alt text](/documentation/img/CutlassDrive.gif)
![Alt text](/documentation/img/pic2.png)

Render from Blender:
![Alt text](/documentation/img/render.png)

The original:
![Alt text](/documentation/img/original.png)
