package com.mcplugindev.slipswhitley.sketchmap.map;

public enum BaseFormat {
	PNG("PNG", 0), JPEG("JPEG", 1);

	private BaseFormat(final String s, final int n) {
	}

	public String getExtension() {
		if (this == BaseFormat.PNG) {
			return "png";
		}
		if (this == BaseFormat.JPEG) {
			return "jpg";
		}
		return null;
	}

	public static BaseFormat fromExtension(final String ext) {
		if (ext.equalsIgnoreCase("png")) {
			return BaseFormat.PNG;
		}
		if (ext.equalsIgnoreCase("jpg")) {
			return BaseFormat.JPEG;
		}
		return null;
	}
}
