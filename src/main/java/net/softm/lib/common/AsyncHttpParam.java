package net.softm.lib.common;


import net.softm.lib.Constant;
import net.softm.lib.Util;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.UnsupportedEncodingException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * AsyncHttpParam
 * @author softm
 */
public class AsyncHttpParam {
	private String serivce = "";
	private String url = "";
	private RequestBody formBody;
	public AsyncHttpParam(String url) {
		this(url,new FormBody.Builder().build());
	}
	
	public AsyncHttpParam(String url, RequestBody formBody) {
		super();
		this.url = url;
		this.formBody = formBody;
		Util.i("url : " + this.url );
	}
	public AsyncHttpParam(String url, FormBody.Builder fBuilder) {
		this(url,fBuilder,"");
	}

	public AsyncHttpParam(String url, FormBody.Builder fBuilder,String s) {
		super();
		this.url = url;
		this.serivce = s;
		this.formBody = fBuilder.add("service",this.serivce).build();
//		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//		this.formBody = this.formBody.create(JSON, "{}");
		
		Util.i("url : " + this.url );
		Util.i("serivce : " + s );
	}
	
//	public AsyncHttpParam(String url, String p, String s) {
//		super();
//		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//		this.serivce= s;
//		this.url = (!Constant.SERVER?url+"/"+s:url);
//		this.formBody = RequestBody.create(JSON, "{p:'" + p+"',service:'" + s + "'}");
//		Util.i("url : " + this.url );
//		Util.i("serivce : " + this.serivce );
//		Util.i("formBody : " + this.formBody );
//	}

	public AsyncHttpParam(String url, String p) {
		this(url,p,"");
	}

	public AsyncHttpParam(String url, String p, String s) {
		super();
		this.serivce= s;
		this.url = url;
		FormBody.Builder fBuilder = new FormBody.Builder();
		try {
			this.formBody = fBuilder.add("p",java.net.URLEncoder.encode(p, "UTF-8")).add("service",this.serivce).build();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Util.i("url : " + this.url );
		Util.i("serivce : " + this.serivce );
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSerivce() {
		return serivce;
	}

	public void setSerivce(String serivce) {
		this.serivce = serivce;
	}

	public RequestBody getFormBody() {
		return formBody;
	}
	public void setFormBody(RequestBody formBody) {
		this.formBody = formBody;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();		
	}

	
}	