package com.hdj.downapp.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * 
 *
 *
 */
public class StringUtil {


	public static boolean isNumber(String str) {
		return str.matches("[0-9]+");
	}
	
	public static CharSequence SpanSize(CharSequence str1,int size) {
		SpannableStringBuilder builder = new SpannableStringBuilder(str1);
		builder.setSpan(new AbsoluteSizeSpan(size), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}
	public static CharSequence SpanColor(CharSequence str1,int color) {
		SpannableStringBuilder builder = new SpannableStringBuilder(str1);
		builder.setSpan(new ForegroundColorSpan(color), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}

	public static CharSequence SpanAppendLn(Object... params) {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		for (int i = 0; i < params.length; i++) {
			CharSequence cs = (CharSequence) params[i];
			if (TextUtils.isEmpty(cs))
				continue;
			if (i != 0)
				builder.append("\n");
			builder.append(cs);
		}
		return builder;
	}
	
	public static CharSequence SpanAppend(Object... params) {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		for (int i = 0; i < params.length; i++) {
			CharSequence cs = (CharSequence) params[i];
			if (TextUtils.isEmpty(cs))
				continue;
			builder.append(cs);
		}
		return builder;
	}
	
	public static CharSequence SpanAppendStrColor(Object... params) {
		SpannableStringBuilder builder2 = new SpannableStringBuilder();
		for (int i = 0; i < params.length; i=i+2) {
			CharSequence cs = (CharSequence) params[i];
			int color=(Integer) params[i+1];
//			int color = (int) params[i+1];
			SpannableStringBuilder builder = new SpannableStringBuilder(cs);
			builder.setSpan(new ForegroundColorSpan(color), 0, cs.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			builder2.append(builder);
		}
		return builder2;
	}
	
	public static String getStr(Context context,int str_id){
		String string = context.getResources().getString(str_id);
		return string;
	}
	public static CharSequence spanNameUid(String name,int uid){
		CharSequence spanSize2 = SpanSize(name, 38);
		CharSequence spanSize3 = SpanSize(" (" + uid + ")",30);
		CharSequence spanColor = SpanColor(spanSize3, Color.GRAY);
		CharSequence spanAppend = SpanAppend(spanSize2,spanColor);
		return spanAppend;
	}
	
	public static CharSequence SpanBoolean(Boolean is) {
		String str = String.valueOf(is);
		SpannableStringBuilder builder = new SpannableStringBuilder(str);
		int length1 = str.length();
		int color = Color.GRAY;
		if (is)
			color = Color.BLUE;
		builder.setSpan(new ForegroundColorSpan(color), 0, length1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}
	
	
	

}
