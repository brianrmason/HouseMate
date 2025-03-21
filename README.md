# HouseMate Controller Service

The HouseMate Controller Service is a command-line interface (CLI) application that coordinates and manages devices, sensors, and events within a smart home environment. Building on the model service, the Controller Service uses event-driven programming to monitor and respond to various changes and commands within the system. It integrates with a Knowledge Graph (KG) to track device statuses, occupant locations, and appliance conditions in real time. The service allows users to set and retrieve the status of devices, as well as define and manage occupant locations within the home, all through an intuitive CLI.

## Features
- **Event-Driven Management**: Respond to commands and sensor data in real time to adjust appliance states and occupant locations.
- **Device Control**: Enable or disable devices, control lights, and manage appliance timers.
- **Occupant Tracking**: Track the presence and location of occupants throughout the house.
- **Event Logging**: Record significant changes for monitoring and analysis.
- **Integration with Knowledge Graph**: Automatically update the Knowledge Graph with state changes and events for better query support.

## Prerequisites
- **Java Development Kit (JDK) 22 or higher**

## Running the HouseMate Controller Service

To compile and run the HouseMate Controller Service, navigate to the project directory and execute the following commands:

### Compile:
javac cscie97/asn2/housemate/model/*.java cscie97/asn3/housemate/controller/*.java cscie97/asn2/housemate/test/*.java cscie97/asn1/knowledge/engine/*.java

### Run 
java -cp . cscie97.asn2.housemate.test.TestDriver housesetup.txt

Command-Line Interface Commands
The CLI allows interaction with the HouseMate Controller Service through specific commands. Here are some of the key commands available:

### Define a Sensor or Device Defines a new sensor or device and assigns it to a specific room in the house.

#### Syntax:

define sensor <sensor_name> type <sensor_type> room <house_name:room_name>
#### Example:

define sensor smoke_detector1 type smoke_detector room house1:kitchen

### Set Device Status Sets the status of a specified device.

#### Syntax:

set <house_name> <room_name> <device_name> status <value>

#### Example:

set house1 kitchen smoke_detector1 status active

### Get Device Status Retrieves the current status of a specific device.

#### Syntax:
getstatus <house_name:room_name:device_name> status

#### Example:
getstatus house1:kitchen:smoke_detector1 status

### Find Occupant by Name Locates an occupant within the house by name.
#### Syntax:
findOccupant <occupant_name>

#### Example:
findOccupant JohnDoe

## Getting Started Tips: 

Run the TestDriver class and use the housesetup.txt file as the initial command set. The housesetup.txt file can be edited to include different CLI commands for testing various configurations and scenarios.
