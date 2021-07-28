package lang.sep3asm;

import java.util.*;
import lang.SymbolTable;

public class Sep3asmSymbolTable extends SymbolTable<LabelEntry> {
	private static final long serialVersionUID = 2434424863156998459L;

	@Override
	public LabelEntry register(String name, LabelEntry e) {
		return null;
	}

	@Override
	public LabelEntry search(String name) {
		return null;
	}

	@Override
	public Integer resolve(String label) {
		HashSet<LabelEntry> visitedSet = new HashSet<LabelEntry>();
		LabelEntry d = get(label);
		while ((d != null) && d.isLabel()) {
			if (visitedSet.contains(d)) {
				return null;
			} // 解決できなかった事実をnull で返す
			visitedSet.add(d);
			d = get(d.getLabel()); // (*)
		}
		if ((d != null) && d.isInteger()) {
			for (LabelEntry e : visitedSet) {
				e.setInteger(d.getInteger());
			}
		}
		return (d == null) ? null : d.getInteger();
	}
}