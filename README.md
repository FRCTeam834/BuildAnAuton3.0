# BuildAnAuton_3.0
The necessary jars can be found in the downloads folder.

Instructions for setup:
<ol>
<li>Put the jar in the same folder as the WPILib jars. This is usually found in [User]/wpilib/user/java/lib. </li>
<li>Make your robot project. </li>
<li>Add the jar to your robot projects build path. For eclipse, go to Properties -> Java Build Path -> Add External Jar and select the downloaded jar. </li>
<li>Go into build.properties and in the userLibs property put in the path of the jar that you just moved. The entire line should be userLibs=[User]/wpilib/user/java/lib/. </li>
<li>In Robot.java, import visualrobot.* and make robot extend VisualRobot. Then, you'll have to implement some methods. Further instructions for that can be found in the sample robot file included in the downloads folder. </li>
</ol>

Function Notes:
<ol>
<li> Add Function: This is used to add points to your auton and display it in the main window
<li> GuidedDraw: Allows you to specify the length of the line and the angle from the east horizontal
<li> Edit Function: Allows you to manipulate the location of points
<li> Select Function: Allows you to add auxiliary motor commands to your auton
<li> Delete Function: Allows you to delete points
<li> Speed Function: Allows you to change the speed of the robot between two points
<li> Turnspeed Function: Allows you to change the turn speed of the robot at a point
<li> Mirror X: Mirrors your auton over the vertical center axis
<li> Mirror Y: Mirrors your auton over the horizontal center axis
<li> Translate: Allows you to move your entire figure at once
<li> Restart: Resets the creation of the auton
<li> Export: Exports the auton to the Robot
<li> CommandEditor, CommandBlock, Command Panel: See BuildAnAuton2
</ol>
