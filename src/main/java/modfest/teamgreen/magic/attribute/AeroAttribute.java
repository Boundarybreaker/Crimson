package modfest.teamgreen.magic.attribute;

import modfest.teamgreen.magic.MagicUser;
import modfest.teamgreen.magic.language.Morpheme;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.IWorld;

public class AeroAttribute extends Attribute {
	public AeroAttribute(Identifier id) {
		super(id, MORPHEME);
	}

	@Override
	public BlockPos[] positions(BlockPos base, int strength) {
		BlockPos result = base.up(RAND.nextInt(16) + 16);

		if (result.getY() > 255) {
			result = new BlockPos(result.getX(), 255, result.getZ());
		}

		return new BlockPos[] {result};
	}

	@Override
	public int activate(IWorld world, MagicUser user, BlockPos pos, ModifyingAttribute modifier) {
		pos = usagePosIfNull(user, pos);
		final BlockPos pos0 = pos;

		world.getEntities(LivingEntity.class, new Box(pos).expand(1.0), le -> true).forEach(le -> le.addVelocity(0.0, 0.7 * modifier.power(world, pos0), 0.0));

		return strength(pos.getY());
	}

	@Override
	public int process(IWorld world, int previous, MagicUser user, BlockPos pos, ModifyingAttribute modifier) {
		pos = usagePosIfNull(user, pos);
		final BlockPos pos0 = pos;

		world.getEntities(LivingEntity.class, new Box(pos).expand(previous == 0 ? 0.5 : previous + 0.5), le -> true).forEach(le -> le.addVelocity(0.0, 0.7 * modifier.power(world, pos0), 0.0));

		return strength(pos.getY());
	}

	@Override
	public double power(IWorld world, BlockPos pos) {
		return strength(pos.getY());
	}

	private static int strength(int y) {
		double result = (int) ((y / 256.0) * 16);
		return result > 15.0 ? 15 : (int) result;
	}

	public static final Morpheme MORPHEME = new Morpheme("hine", "hi", "hi", true);
}
