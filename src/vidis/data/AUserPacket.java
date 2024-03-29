/*	VIDIS is a simulation and visualisation framework for distributed systems.
	Copyright (C) 2009 Dominik Psenner, Christoph Caks
	This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
	You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>. */
package vidis.data;

import vidis.data.exceptions.ObstructInitCallException;
import vidis.data.mod.AUserComponent;
import vidis.data.mod.IUserLink;
import vidis.data.mod.IUserNode;
import vidis.data.mod.IUserPacket;
import vidis.data.sim.ISimPacketCon;
import vidis.data.var.vars.AVariable;
import vidis.data.var.vars.AVariable.COMMON_SCOPES;

/**
 * abstract user packet represents a packet by a user;
 * 
 * module writers should use this class to write their own packet
 * 
 * @author dominik
 * 
 */
public abstract class AUserPacket extends AUserComponent implements IUserPacket {

    protected ISimPacketCon simulatorComponent;

    public void init(ISimPacketCon simulatorComponent) throws ObstructInitCallException {
		if (this.simulatorComponent != null) {
	//	    Logger
	//		    .output(LogLevel.ERROR, this, "init(" + simulatorComponent
	//			    + "), but already registered at "
	//			    + this.simulatorComponent);
		    throw new ObstructInitCallException();
		}
		this.simulatorComponent = simulatorComponent;
    }

    public final IUserNode getSource() {
    	return simulatorComponent.getFrom();
    }

    public IUserLink getLinkToSource() {
    	return simulatorComponent.getLink();
    }

    public final void execute() {
    	// no action at packets :-)
    }

    public String toString() {
    	return "Packet#" + hashCode();
    }

    public final void interrupt() {
		try {
		    simulatorComponent.interrupt();
		} catch (NullPointerException e) {
		    // nothing
		}
    }

    public final void sleep(int steps) {
		try {
		    simulatorComponent.sleep(steps);
		} catch (NullPointerException e) {
		    // nothing
		}
    }

    public final AVariable getVariable(String identifier) {
    	if(simulatorComponent != null)
    		return simulatorComponent.getScopedVariable(COMMON_SCOPES.USER, identifier);
    	return null;
    }

    public final boolean hasVariable(String identifier) {
    	if(simulatorComponent != null)
    		return simulatorComponent.hasScopedVariable(COMMON_SCOPES.USER, identifier);
    	return false;
    }
}
