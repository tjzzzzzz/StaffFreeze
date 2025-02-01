# StaffFreeze 
<img src="https://github.com/user-attachments/assets/1776db64-5e82-4389-974b-60ce127c741a" width="300">



## Overview
StaffFreeze is a Minecraft plugin designed for server staff to effectively manage and monitor players during screenshare or investigation processes.

## Features

### Core Functionality
- **Player Freezing**: Staff can freeze/unfreeze players using `/freeze` or `/ss` commands
- **Prevents frozen players from:**
  - Moving
  - Placing blocks
  - Taking damage
  - Logging out (with staff notification)

### Commands
- `/freeze <player>` or `/ss <player>` - Freeze/unfreeze a player
- `/lastfreeze <player>` or `/lf <player>` - Check when a player was last frozen
- `/stafffreezereload` - Reload the plugin configuration

### Special Features
- **Chat Marking**: Frozen players' messages are marked with "[Frozen]" for staff visibility
- **Periodic Reminders**: Frozen players receive regular freeze messages
- **Staff Broadcasts**: All freeze/unfreeze actions are broadcast to staff
- **Last Freeze Tracking**: Track when players were last frozen

### Permissions
- `stafffreeze.use` - Allows freezing players and seeing freeze notifications
- `stafffreeze.reload` - Allows reloading the plugin configuration

### Configuration
- Fully customizable messages
- Configurable prefix
- Custom freeze message with Discord information
- Broadcast messages for staff

## Technical Requirements
- Paper/Spigot
- Permissions system compatibility(If you want to use it)

## Installation
1. Place the plugin JAR in your server's plugins folder
2. Start/restart your server
3. Configure messages in `config.yml` as needed
4. Grant necessary permissions to staff members
