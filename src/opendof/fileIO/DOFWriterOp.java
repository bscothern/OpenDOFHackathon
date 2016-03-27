package opendof.fileIO;

import java.nio.file.StandardOpenOption;

public enum DOFWriterOp {
	Append, Overwrite, OnCreate;
	
	public StandardOpenOption toOption() {
		switch (this.ordinal()) {
		case 0:
			return StandardOpenOption.APPEND;
		case 1:
			return StandardOpenOption.CREATE_NEW;
		case 2:
			return StandardOpenOption.CREATE;
		}
		return null;
	}
}
