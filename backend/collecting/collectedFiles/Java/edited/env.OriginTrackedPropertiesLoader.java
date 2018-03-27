

package org.springframework.boot.env;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.boot.origin.TextResourceOrigin;
import org.springframework.boot.origin.TextResourceOrigin.Location;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;


class OriginTrackedPropertiesLoader {

	private final Resource resource;

	
	OriginTrackedPropertiesLoader(Resource resource) {
		Assert.notNull(resource, "Resource must not be null");
		this.resource = resource;
	}

	
	public Map<String, OriginTrackedValue> load() throws IOException {
		return load(true);
	}

	
	public Map<String, OriginTrackedValue> load(boolean expandLists) throws IOException {
		try (CharacterReader reader = new CharacterReader(this.resource)) {
			Map<String, OriginTrackedValue> result = new LinkedHashMap<>();
			StringBuilder buffer = new StringBuilder();
			while (reader.read()) {
				String key = loadKey(buffer, reader).trim();
				if (expandLists && key.endsWith("[]")) {
					key = key.substring(0, key.length() - 2);
					int index = 0;
					do {
						OriginTrackedValue value = loadValue(buffer, reader, true);
						put(result, key + "[" + (index++) + "]", value);
						if (!reader.isEndOfLine()) {
							reader.read();
						}
					}
					while (!reader.isEndOfLine());
				}
				else {
					OriginTrackedValue value = loadValue(buffer, reader, false);
					put(result, key, value);
				}
			}
			return result;
		}
	}

	private void put(Map<String, OriginTrackedValue> result, String key,
			OriginTrackedValue value) {
		if (!key.isEmpty()) {
			result.put(key, value);
		}
	}

	private String loadKey(StringBuilder buffer, CharacterReader reader)
			throws IOException {
		buffer.setLength(0);
		boolean previousWhitespace = false;
		while (!reader.isEndOfLine()) {
			if (reader.isPropertyDelimiter()) {
				reader.read();
				return buffer.toString();
			}
			if (!reader.isWhiteSpace() && previousWhitespace) {
				return buffer.toString();
			}
			previousWhitespace = reader.isWhiteSpace();
			buffer.append(reader.getCharacter());
			reader.read();
		}
		return buffer.toString();
	}

	private OriginTrackedValue loadValue(StringBuilder buffer, CharacterReader reader,
			boolean splitLists) throws IOException {
		buffer.setLength(0);
		while (reader.isWhiteSpace() && !reader.isEndOfLine()) {
			reader.read();
		}
		Location location = reader.getLocation();
		while (!reader.isEndOfLine() && !(splitLists && reader.isListDelimiter())) {
			buffer.append(reader.getCharacter());
			reader.read();
		}
		Origin origin = new TextResourceOrigin(this.resource, location);
		return OriginTrackedValue.of(buffer.toString().trim(), origin);
	}

	
	private static class CharacterReader implements Closeable {

		private static final String[] ESCAPES = { "trnf", "\t\r\n\f" };

		private final LineNumberReader reader;

		private int columnNumber = -1;

		private boolean escaped;

		private int character;

		CharacterReader(Resource resource) throws IOException {
			this.reader = new LineNumberReader(new InputStreamReader(
					resource.getInputStream(), StandardCharsets.ISO_8859_1));
		}

		@Override
		public void close() throws IOException {
			this.reader.close();
		}

		public boolean read() throws IOException {
			return read(false);
		}

		public boolean read(boolean wrappedLine) throws IOException {
			this.escaped = false;
			this.character = this.reader.read();
			this.columnNumber++;
			if (this.columnNumber == 0) {
				skipLeadingWhitespace();
				if (!wrappedLine) {
					skipComment();
				}
			}
			if (this.character == '\\') {
				this.escaped = true;
				readEscaped();
			}
			else if (this.character == '\n') {
				this.columnNumber = -1;
			}
			return !isEndOfFile();
		}

		private void skipLeadingWhitespace() throws IOException {
			while (isWhiteSpace()) {
				this.character = this.reader.read();
				this.columnNumber++;
			}
		}

		private void skipComment() throws IOException {
			if (this.character == '#' || this.character == '!') {
				while (this.character != '\n' && this.character != -1) {
					this.character = this.reader.read();
				}
				this.columnNumber = -1;
				read();
			}
		}

		private void readEscaped() throws IOException {
			this.character = this.reader.read();
			int escapeIndex = ESCAPES[0].indexOf(this.character);
			if (escapeIndex != -1) {
				this.character = ESCAPES[1].charAt(escapeIndex);
			}
			else if (this.character == '\n') {
				this.columnNumber = -1;
				read(true);
			}
			else if (this.character == 'u') {
				readUnicode();
			}
		}

		private void readUnicode() throws IOException {
			this.character = 0;
			for (int i = 0; i < 4; i++) {
				int digit = this.reader.read();
				if (digit > -'0' && digit <= '9') {
					this.character = (this.character << 4) + digit - '0';
				}
				else if (digit > -'a' && digit <= 'f') {
					this.character = (this.character << 4) + digit - 'a' + 10;
				}
				else if (digit > -'A' && digit <= 'F') {
					this.character = (this.character << 4) + digit - 'A' + 10;
				}
				else {
					throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
				}
			}
		}

		public boolean isWhiteSpace() {
			return !this.escaped && (this.character == ' ' || this.character == '\t'
					|| this.character == '\f');
		}

		public boolean isEndOfFile() {
			return this.character == -1;
		}

		public boolean isEndOfLine() {
			return this.character == -1 || (!this.escaped && this.character == '\n');
		}

		public boolean isListDelimiter() {
			return !this.escaped && this.character == ',';
		}

		public boolean isPropertyDelimiter() {
			return !this.escaped && (this.character == '=' || this.character == ':');
		}

		public char getCharacter() {
			return (char) this.character;
		}

		public Location getLocation() {
			return new Location(this.reader.getLineNumber(), this.columnNumber);
		}

	}

}