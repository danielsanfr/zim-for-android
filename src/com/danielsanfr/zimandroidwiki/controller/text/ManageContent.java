package com.danielsanfr.zimandroidwiki.controller.text;

import java.util.Scanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;

public class ManageContent {

	private static final String BOLD = "**";
	private static final String ITALIC = "//";
	private static final String UNDERLINE = "__";
	private static final String STRIKETHROUGH = "~~";
	private static final String VERBATIM = "\'\'";
	private static final int TITLE_COLOR = Color.rgb(255, 187, 51);
	private int countSize;
	private int lineSize;
	private SpannableStringBuilder grossContent;
	private SpannableStringBuilder refinedContent;
	private Context context;

	public ManageContent(SpannableStringBuilder grossContent, Context context) {
		// TODO Auto-generated constructor stub
		this.grossContent = new SpannableStringBuilder(grossContent);
		refinedContent = grossContent;
		this.context = context;
	}

	@SuppressLint("UseValueOf")
	public void generateRefinedContent() {
		countSize = 0;
		Scanner scanner = new Scanner(grossContent.toString());
		while (scanner.hasNextLine()) {
			lineSize = -1;
			String line = scanner.nextLine();
			if (!isHeader(line)) {
				line = checkBasicFormat(line, BOLD);
				line = checkBasicFormat(line, ITALIC);
				line = checkBasicFormat(line, UNDERLINE);
				line = checkBasicFormat(line, STRIKETHROUGH);
//				line = checkBasicFormat(line, VERBATIM);
				
				line = checkLinkGenerick(line);
				line = checkLinkWeb(line);
				// checkImage(line);
			}
			++countSize;
			if (lineSize == -1)
				countSize += line.length();
			else
				countSize += lineSize;
		}
	}

	private Boolean isHeader(String line) {
		int countEqual = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '=')
				++countEqual;
			else if (line.charAt(i) == ' ') {
				if (countEqual > 1
						&& line.endsWith(" " + repeatChar('=', countEqual))) {

					refinedContent.replace(countSize,
							countSize + line.indexOf(" ") + 1, "");
					line = line.substring(line.indexOf(" ") + 1);
					refinedContent.replace(countSize + line.length()
							- countEqual - 1, countSize + line.length(), "");
					line = line.substring(0, line.length() - countEqual - 1);

					lineSize = line.length();
					setBasicHeader(line.length());
					if (countEqual == 6) {
						refinedContent.setSpan(new UnderlineSpan(), countSize,
								countSize + line.length(), 0); // Change style
						refinedContent.setSpan(new RelativeSizeSpan(1.5f),
								countSize, countSize + lineSize, 0); // Change
																		// size
					} else if (countEqual == 5) {
						refinedContent.setSpan(new RelativeSizeSpan(1.2f),
								countSize, countSize + lineSize, 0); // Change
																		// size
					} else if (countEqual == 4) {
						refinedContent.setSpan(new RelativeSizeSpan(1.1f),
								countSize, countSize + lineSize, 0); // Change
																		// size
						refinedContent.setSpan(new StyleSpan(Typeface.ITALIC),
								countSize, countSize + lineSize, 0); // Change
																		// size
					} else if (countEqual == 3) {
						refinedContent.setSpan(new RelativeSizeSpan(1.05f),
								countSize, countSize + lineSize, 0); // Change
																		// size
					}
					return true;
				}
			}
		}
		return false;
	}

	private String checkBasicFormat(String line, String format) {
		StringBuffer stringBuffer = new StringBuffer(line);
		String restOfTheLine = line;
		while (restOfTheLine.contains(format)) {
			int begin = restOfTheLine.indexOf(format, 0);
			while (restOfTheLine.indexOf(format, begin + 1) == begin + 1)
				++begin;
			int end = restOfTheLine.indexOf(format, begin + 2);
			if (begin != -1 && end != -1) {
				refinedContent
						.replace(countSize + end, countSize + end + 2, "");
				refinedContent.replace(countSize + begin,
						countSize + begin + 2, "");
				if (format.compareTo(BOLD) == 0)
					refinedContent.setSpan(new StyleSpan(Typeface.BOLD),
							countSize + begin, countSize + end - 1, 0); // Change
																		// style
				else if (format.compareTo(ITALIC) == 0)
					refinedContent.setSpan(new StyleSpan(Typeface.ITALIC),
							countSize + begin, countSize + end - 1, 0); // Change
																		// style
				else if (format.compareTo(UNDERLINE) == 0)
					refinedContent.setSpan(new UnderlineSpan(), countSize
							+ begin, countSize + end - 1, 0); // Change style
				else if (format.compareTo(STRIKETHROUGH) == 0)
					refinedContent.setSpan(new StrikethroughSpan(), countSize
							+ begin, countSize + end - 1, 0); // Change style
//				else if (format.compareTo(VERBATIM) == 0)
//					refinedContent.setSpan(
//							new StyleSpan(Typeface.createFromAsset(
//									context.getAssets(),
//									"fonts/.ttf").getStyle()),
//							countSize + begin, countSize + end - 1, 0); // Change
																		// style
				stringBuffer.replace(end, end + 2, "");
				stringBuffer.replace(begin, begin + 2, "");
				restOfTheLine = stringBuffer.toString();
			} else
				break;
		}
		lineSize = restOfTheLine.length();
		return restOfTheLine;
	}

	private String checkLinkWeb(String line) {
		String[] types = { "http://", "https://", "ftp://" };
		String link;
		String restOfTheLine = line;
		for (String type : types) {
			while (restOfTheLine.contains(type)) {
				int begin = restOfTheLine.indexOf(type, 0);
				int end = restOfTheLine.indexOf(" ", begin + 5);
				if (begin != -1) {
					if (end == -1) {
						link = restOfTheLine.substring(begin,
								restOfTheLine.length());
						if (restOfTheLine.length() > begin + 9)
							refinedContent.setSpan(new URLSpan(link), countSize
									+ begin,
									countSize + begin + restOfTheLine.length(),
									0); // Change
						// style
						restOfTheLine = restOfTheLine.substring(begin + 5);
					} else {
						link = restOfTheLine.substring(begin, end);
						if (end > begin + 9)
							refinedContent.setSpan(new URLSpan(link), countSize
									+ begin, countSize + end, 0); // Change
						// style
						restOfTheLine = restOfTheLine.substring(end);
					}
				} else
					break;
			}
		}
		return restOfTheLine;
	}

	private String checkLinkGenerick(String line) {
		StringBuffer stringBuffer = new StringBuffer(line);
		String link;
		String restOfTheLine = line;
		while (restOfTheLine.contains("[[")) {
			int begin = restOfTheLine.indexOf("[[", 0);
			int end = restOfTheLine.indexOf("]]", begin + 2);
			if (begin != -1 && end != -1) {
				int endAux = restOfTheLine.indexOf("|", begin + 2);
				if (endAux == -1) {
					link = restOfTheLine.substring(begin + 2, end);
					refinedContent.replace(countSize + end,
							countSize + end + 2, "");
					refinedContent.replace(countSize + begin, countSize + begin
							+ 2, "");
					stringBuffer.replace(end, end + 2, "");
					stringBuffer.replace(begin, begin + 2, "");
					refinedContent.setSpan(new URLSpan(link),
							countSize + begin, countSize + end - 1, 0); // Change
					// style
				} else {
					link = restOfTheLine.substring(begin + 2, endAux);
					refinedContent.replace(countSize + end,
							countSize + end + 2, "");
					refinedContent.replace(countSize + begin, countSize
							+ endAux + 1, "");
					stringBuffer.replace(end, end + 2, "");
					stringBuffer.replace(begin, endAux + 1, "");
					refinedContent.setSpan(new URLSpan(link), countSize + begin
							- 1, countSize + end - link.length() - 1, 0); // Change
					// style
				}
				restOfTheLine = stringBuffer.toString();
			} else
				break;
		}
		lineSize = restOfTheLine.length();
		return restOfTheLine;
	}

	private void checkImage(String line) {
		String restOfTheLine = line;
		int start = 0;
		int finish = 0;
		while (restOfTheLine.contains("{{")) {
			int begin = restOfTheLine.indexOf("{{", 0);
			int end = restOfTheLine.indexOf("}}", begin + 2);
			start += begin;
			finish += (end + 2);
			if (begin != -1 && end != -1) {
				int endAux = restOfTheLine.indexOf("?type=", begin + 2);
				if (endAux != -1)
					refinedContent.setSpan(
							new URLSpan(restOfTheLine.substring(start + 2,
									finish + endAux - end - 2)), countSize
									+ start, countSize + finish, 0); // Change
																		// style
				else
					refinedContent.setSpan(
							new URLSpan(restOfTheLine.substring(start + 2,
									finish - 2)), countSize + start, countSize
									+ finish, 0); // Change
													// style
			} else {
				if (begin == -1)
					++start;
				if (end == -1)
					++finish;
			}
			if (end != -1 && restOfTheLine.length() > end + 1)
				restOfTheLine = restOfTheLine.substring(end + 1);
			else
				break;
			start += (end - start + 1);
		}
	}

	private void setBasicHeader(int end) {
		refinedContent.setSpan(new ForegroundColorSpan(TITLE_COLOR), countSize,
				countSize + end, 0); // Change color
		refinedContent.setSpan(new StyleSpan(Typeface.BOLD), countSize,
				countSize + end, 0); // Change style
	}

	public String repeatChar(char character, int quant) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < quant; i++) {
			stringBuffer.append(character);
		}
		return stringBuffer.toString();
	}

	public String getGrossContent() {
		return grossContent.toString();
	}

	public void setGrossContent(SpannableStringBuilder grossContent) {
		this.grossContent = grossContent;
	}

	public SpannableStringBuilder getRefinedContent() {
		return refinedContent;
	}

}
