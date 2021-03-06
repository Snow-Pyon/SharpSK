package me.sharpjaws.sharpSK;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import me.sharpjaws.sharpSK.Threads.CTickTimerThread;
import me.sharpjaws.sharpSK.Threads.CTimerThread;

public class EffTimerCreate extends Effect {
	private Expression<String> s;
	private Expression<Timespan> duration;
	private Expression<Boolean> active;
	int task;
	int mark;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult result) {
		s = (Expression<String>) expr[0];
		duration = (Expression<Timespan>) expr[1];
		active = (Expression<Boolean>) expr[2];
		this.mark = result.mark;
		return true;
	}
	

	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "timer create %string% duration %timespan%";
	}

	@Override
	protected void execute(final Event e) {
		
		Boolean exist = false;
		for (Thread t : Thread.getAllStackTraces().keySet()) {
	        if (t instanceof CTimerThread) {
	        	CTimerThread ti = (CTimerThread)t;
	        	if (ti.getName().equals(s.getSingle(e))){	        
	        		exist = true;
	        	}
	        }
	        if (t instanceof CTickTimerThread) {
	        	CTickTimerThread ti = (CTickTimerThread)t;
	        	if (ti.getName().equals(s.getSingle(e))){	        
	        		exist = true;
	        	}
	        }
		}
	        
		if (exist != true){
			
			System.out.println(mark);
	if (mark == -1){		
	if (active == null)	{
		CTimerThread th = new CTimerThread(s.getSingle(e),duration.getSingle(e).getTicks()/20, false);
		th.instance().start();
	}else {
	if (active.getSingle(e) == false){
	CTimerThread th = new CTimerThread(s.getSingle(e),duration.getSingle(e).getTicks()/20, false);
	th.instance().start();
	}else if (active.getSingle(e) == true){
	CTimerThread th = new CTimerThread(s.getSingle(e),duration.getSingle(e).getTicks()/20, true);
	th.instance().start();
	}
}
		
	}else if(mark == 1){
		if (active == null)	{
			CTickTimerThread th = new CTickTimerThread(s.getSingle(e),(int)duration.getSingle(e).getTicks_i(), false);
			th.instance().start();
		}else {
		if (active.getSingle(e) == false){
		CTickTimerThread th = new CTickTimerThread(s.getSingle(e),(int)duration.getSingle(e).getTicks_i(), false);
		th.instance().start();
		}else if (active.getSingle(e) == true){
		CTickTimerThread th = new CTickTimerThread(s.getSingle(e),(int)duration.getSingle(e).getTicks_i(), true);
		th.instance().start();
		}
	}
	}
	
	}else{
		main core = (main)Bukkit.getPluginManager().getPlugin("SharpSK");
		core.getLogger().warning("Timer "+ s.getSingle(e) + " could not be created because a timer with the same name is already running.");
	}
	
		
		
	}	
}
