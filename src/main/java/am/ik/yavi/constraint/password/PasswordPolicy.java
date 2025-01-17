/*
 * Copyright (C) 2018-2021 Toshiaki Maki <makingx@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package am.ik.yavi.constraint.password;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @param <T> target class
 * @since 0.7.0
 */
public interface PasswordPolicy<T> extends Predicate<T> {

	default String name() {
		return this.getClass().getSimpleName()
				.replace(PasswordPolicy.class.getSimpleName(), "");
	}

	static <T> PasswordPolicy<T> of(String name, Predicate<T> predicate) {
		return new PasswordPolicy<T>() {
			@Override
			public boolean test(T t) {
				return predicate.test(t);
			}

			@Override
			public String name() {
				return name;
			}
		};
	}

	static <T extends CharSequence> PasswordPolicy<T> pattern(String name, String regex) {
		final Pattern pattern = Pattern.compile(regex);
		return new PasswordPolicy<T>() {
			@Override
			public boolean test(T input) {
				return pattern.matcher(input).find();
			}

			@Override
			public String name() {
				return name;
			}
		};
	}

	PasswordPolicy<String> UPPERCASE = PasswordPolicy.pattern("Uppercase", "[A-Z]");

	PasswordPolicy<String> LOWERCASE = PasswordPolicy.pattern("Lowercase", "[a-z]");

	PasswordPolicy<String> NUMBERS = PasswordPolicy.pattern("Numbers", "[0-9]");

	PasswordPolicy<String> SYMBOLS = PasswordPolicy.pattern("Symbols",
			"[!-/:-@\\[-`{-\\~]");
}
