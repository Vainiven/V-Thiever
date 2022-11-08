import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import simple.api.filters.SimpleSkills.Skill;
import simple.api.script.Category;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;

@ScriptManifest(author = "Vainiven & FVZ", category = Category.THIEVING, description = "Will get you that level 99 thieving.", discord = "Vainiven#6986", name = "V-Thiever", servers = {
		"SpawnPK" }, version = "0.1")

public class Thiever extends Script implements SimplePaintable {

	// PAINT
	String status;
	long startTime;
	int thiefs;
	private final Color color1 = new Color(255, 255, 255);
	final Font font1 = new Font("Impact", 1, 13), font2 = new Font("Impact", 0, 13);
	private final Image showImage = ctx.paint.getImage("https://i.imgur.com/QM6ilbx.png");

	@Override
	public boolean onExecute() {
		ctx.log("Welcome to V-Thiever: " + ctx.players.getLocal().getName() + "!");
		startTime = System.currentTimeMillis();
		status = "Welcome to V-Thiever";
		return true;
	}

	@Override
	public void onProcess() {
		if (ctx.players.getLocal().getLocation().getRegionID() == 12342) {
			if (!ctx.objects.populate().filter(table()).isEmpty()) {
				ctx.objects.next().interact(900);
				ctx.onCondition(() -> ctx.players.getLocal().getAnimation() == -1, 950, 5);
				thiefs++;
			}
		} else {
			ctx.magic.castHomeTeleport();
		}

	}

	@Override
	public void onTerminate() {
		ctx.log("Thank you for using V-Thiever: " + ctx.players.getLocal().getName() + "!");

	}

	private int table() {
		final int thievingLevel = ctx.skills.getRealLevel(Skill.THIEVING);
		if (thievingLevel < 26) {
			return 4874;
		}
		if (thievingLevel > 25 && thievingLevel < 51) {
			return 4875;
		}
		if (thievingLevel > 50 && thievingLevel < 76) {
			return 4876;
		}
		if (thievingLevel >= 75 && thievingLevel < 99) {
			return 4877;
		}
		return 4878;
	}

	@Override
	public void onPaint(Graphics2D g1) {
		final Graphics2D g = g1;
		g.drawImage(showImage, 1, 338, null);
		g.setFont(font2);
		g.setColor(color1);
		g.setFont(font2);
		g.drawString("" + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 445, 379);
		g.drawString("Status: " + status, 30, 379);
		g.drawString("Total Thiefs: " + thiefs, 30, 395);
		g.drawString("Thiefs per hour: " + ctx.paint.valuePerHour(thiefs, startTime), 30, 411);

	}
}
