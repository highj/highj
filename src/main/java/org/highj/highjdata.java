package org.highj;

import org.derive4j.Data;
import org.derive4j.Derive;
import org.derive4j.Flavour;
import org.derive4j.Visibility;

@Data(value=@Derive(inClass = "{ClassName}Impl", withVisibility = Visibility.Package), flavour = Flavour.HighJ)
public @interface highjdata {}
