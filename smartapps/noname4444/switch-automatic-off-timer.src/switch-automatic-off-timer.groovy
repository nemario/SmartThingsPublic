/**
 *  Switch Automatic Off Timer
 *
 *  Copyright 2015 Jim Worley
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Switch Automatic Off Timer",
    namespace: "noname4444",
    author: "Jim Worley",
    description: "When a switch is turned on using a physical switch, turn off the switch after a certain amount of time.",
    category: "Convenience",
    iconUrl: "https://lh3.ggpht.com/dYBCDKcbtxasy9F3dcQ4AClMftSJOIKr42TK7LjrxyEIpKITWO6jQMXKdw5-s35lJBE=w300-rw",
    iconX2Url: "https://lh3.ggpht.com/dYBCDKcbtxasy9F3dcQ4AClMftSJOIKr42TK7LjrxyEIpKITWO6jQMXKdw5-s35lJBE=w300-rw",
    iconX3Url: "https://lh3.ggpht.com/dYBCDKcbtxasy9F3dcQ4AClMftSJOIKr42TK7LjrxyEIpKITWO6jQMXKdw5-s35lJBE=w300-rw")


preferences {
	section {
		input(name: "phySwitch", type: "capability.switch", title: "For these switches...", multiple: true, required: true)
        input(name: "timerDelay", type: "number",title: "Turn off in... (in minutes)", required: true, defaultValue:5)
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(phySwitch,"switch.on",turnOffIn)
}

// TODO: implement event handlers
def turnOffIn(evt){
    log.debug "Ohh, ${evt.descriptionText} happened, lets see if I can do anything!"
	if (evt.isPhysical()) {
		log.debug "The event was physical, starting off timer!"
        runIn(60*timerDelay,"turnOffSwitch")
	} else {
		log.debug "Aww, the event was ditital, I don't get to do anything."
	}
}

def turnOffSwitch(){
  phySwitch*.off()
}