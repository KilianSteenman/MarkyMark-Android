/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkcommon.markdownitems.inline;

import com.m2mobi.markymark.item.inline.MarkDownString;

/**
 * {@link MarkDownString} used for storing links in text
 */
public class LinkString extends MarkDownString {

	private final String mUrl;

	public LinkString(final String pContent, String pUrl, final boolean pCanHasChildItems) {
		super(pContent, pCanHasChildItems);
		mUrl = pUrl;
	}

	public String getUrl() {
		return mUrl;
	}
}