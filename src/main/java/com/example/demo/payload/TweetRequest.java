package com.example.demo.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author yosuk
 *
 */
public class TweetRequest {

	@NotBlank
	@Size(min = 1, max = 140)
	private String text;

}
