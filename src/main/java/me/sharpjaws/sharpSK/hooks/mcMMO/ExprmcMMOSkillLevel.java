package me.sharpjaws.sharpSK.hooks.mcMMO;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprmcMMOSkillLevel extends SimpleExpression<Number> {
	private Expression<Player> p;
	private Expression<SkillType> s;

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		s = (Expression<SkillType>) expr[0];
		p = (Expression<Player>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[mcmmo] %skilltype% level of %player%";
	}

	@Override
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[] {ExperienceAPI.getLevel((Player)p.getSingle(e), (String)s.getSingle(e).toString()) };
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			
			Number level = (Number)delta[0];
			ExperienceAPI.setLevel(p.getSingle(e), s.getSingle(e).toString(), level.intValue());
		}
	if (mode == Changer.ChangeMode.ADD) {
			
			Number level = (Number)delta[0];
			ExperienceAPI.addLevel(p.getSingle(e), s.getSingle(e).toString(), level.intValue());
		}
	if (mode == Changer.ChangeMode.REMOVE) {
		
		Number level = (Number)delta[0];
		if (ExperienceAPI.getLevel(p.getSingle(e),s.getSingle(e).toString())>0){
			if (ExperienceAPI.getLevel(p.getSingle(e), s.getSingle(e).toString()) < level.intValue() ) {
				ExperienceAPI.setLevel(p.getSingle(e), s.getSingle(e).toString(),0);
			}else{
				ExperienceAPI.setLevel(p.getSingle(e), s.getSingle(e).toString(), ExperienceAPI.getLevel(p.getSingle(e), s.getSingle(e).toString()) - level.intValue());
			}
		}
		}
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.ADD)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.REMOVE)
			return CollectionUtils.array(new Class[] { Number.class });
		return null;
	}
}
