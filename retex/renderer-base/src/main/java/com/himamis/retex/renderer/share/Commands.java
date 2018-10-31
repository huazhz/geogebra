/* Commands.java
 * =========================================================================
 * This file is part of the JLaTeXMath Library - http://forge.scilab.org/jlatexmath
 *
 * Copyright (C) 2018 DENIZET Calixte
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * A copy of the GNU General Public License can be found in the file
 * LICENSE.txt provided with the source distribution of this program (see
 * the META-INF directory in the source jar). This license can also be
 * found on the GNU website at http://www.gnu.org/licenses/gpl.html.
 *
 * If you did not receive a copy of the GNU General Public License along
 * with this program, contact the lead developer, or write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 * Linking this library statically or dynamically with other modules
 * is making a combined work based on this library. Thus, the terms
 * and conditions of the GNU General Public License cover the whole
 * combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce
 * an executable, regardless of the license terms of these independent
 * modules, and to copy and distribute the resulting executable under terms
 * of your choice, provided that you also meet, for each linked independent
 * module, the terms and conditions of the license of that module.
 * An independent module is a module which is not derived from or based
 * on this library. If you modify this library, you may extend this exception
 * to your version of the library, but you are not obliged to do so.
 * If you do not wish to do so, delete this exception statement from your
 * version.
 *
 */

package com.himamis.retex.renderer.share;

import java.util.HashMap;
import java.util.Map;

import com.himamis.retex.renderer.share.commands.*;
import com.himamis.retex.renderer.share.exception.ParseException;
import com.himamis.retex.renderer.share.platform.font.Font;
import com.himamis.retex.renderer.share.platform.graphics.Color;

public class Commands {

	private static final Map<String, Command> reusableMap = new HashMap<>();

	private static final Command dollar = new CommandDollars.Dollar(true,
			TeXConstants.STYLE_TEXT);
	private static final Command dollardollar = new CommandDollars.Dollar(false,
			TeXConstants.STYLE_DISPLAY);

	private static Command getCommand(String s) {

		switch (s) {

		// XXX
		// case "usepackage": return new CommandUsePackage();

		case "ce":
			return new CommandCE();

		case "bond":
			return new CommandBond();

		case "hbox":
			return new CommandHBox();

		case "cancel":
			return new CommandCancel();

		case "bcancel":
			return new CommandBCancel();

		case "xcancel":
			return new CommandXCancel();

		case "mathchoice":
			return new CommandMathChoice();

		case "pod":
			return new CommandPod();

		case "bmod":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom sp = new SpaceAtom(TeXLength.Unit.MU, 5.);
					final RowAtom ra = new RowAtom(sp,
							new RomanAtom(TeXParser
									.getAtomForLatinStr("mod", true).changeType(
											TeXConstants.TYPE_BINARY_OPERATOR)),
							sp);
					return ra;
				}
			};
		case "pmod":
			return new CommandPMod();

		case "mod":
			return new CommandMod();

		case "begingroup":
			return new CommandBeginGroup();

		case "endgroup":
			return new CommandEndGroup();

		case "DeclareMathOperator":
			return new CommandDeclareMathOperator();

		case "newcommand":
			return new CommandNewCommand();

		case "renewcommand":
			return new CommandRenewCommand();

		case "newenvironment":
			return new CommandNewEnvironment();

		case "renewenvironment":
			return new CommandRenewEnvironment();

		case "left":
			return new CommandLMR.CommandLeft();
		case "right":
			return new CommandLMR.CommandRight();
		case "middle":
			return new CommandLMR.CommandMiddle();

		// stretchy versions
		case "Braket":
			return new CommandBra(Symbols.LANGLE, Symbols.RANGLE);
		case "Bra":
			return new CommandBra(Symbols.LANGLE, Symbols.VERT);
		case "Ket":
			return new CommandBra(Symbols.VERT, Symbols.RANGLE);
		case "Set":
			return new CommandBra(Symbols.LBRACE, Symbols.RBRACE);
		case "braket":
			return new CommandBraKet();

		// non-stretchy versions
		case "bra":
			return new CommandBra2();
		case "ket":
			return new CommandKet();
		case "set":
			return new CommandSet();

		case "hookrightarrow":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(3);
					// XXX was -0.169
					ra.add(Symbols.LHOOK,
							new SpaceAtom(TeXLength.Unit.EM, -0.43),
							Symbols.RIGHTARROW);
					ra.setShape(true);
					return new TypedAtom(TeXConstants.TYPE_RELATION, ra);
				}
			};
		case "hookleftarrow":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(3);
					ra.add(Symbols.LEFTARROW,
							// XXX was -0.169
							new SpaceAtom(TeXLength.Unit.EM, -0.43),
							Symbols.RHOOK);
					ra.setShape(true);
					return new TypedAtom(TeXConstants.TYPE_RELATION, ra);
				}
			};
		case "Longrightarrow":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(3);
					ra.add(Symbols.BIG_RELBAR,
							new SpaceAtom(TeXLength.Unit.EM, -0.177),
							Symbols.BIG_RIGHTARROW);
					ra.setShape(true);
					return new TypedAtom(TeXConstants.TYPE_RELATION, ra);
				}
			};
		case "Longleftarrow":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(3);
					ra.add(Symbols.BIG_LEFTARROW,
							new SpaceAtom(TeXLength.Unit.EM, -0.177),
							Symbols.BIG_RELBAR);
					ra.setShape(true);
					return new TypedAtom(TeXConstants.TYPE_RELATION, ra);
				}
			};
		case "longleftarrow":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(3);
					ra.add(Symbols.LEFTARROW,
							new SpaceAtom(TeXLength.Unit.EM, -0.206),
							Symbols.MINUS
									.changeType(TeXConstants.TYPE_RELATION));
					ra.setShape(true);
					return new TypedAtom(TeXConstants.TYPE_RELATION, ra);
				}
			};
		case "longrightarrow":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(3);
					ra.add(Symbols.MINUS.changeType(TeXConstants.TYPE_RELATION),
							new SpaceAtom(TeXLength.Unit.EM, -0.206),
							Symbols.RIGHTARROW);
					ra.setShape(true);
					return new TypedAtom(TeXConstants.TYPE_RELATION, ra);
				}
			};
		case "longleftrightarrow":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(3);
					ra.add(Symbols.LEFTARROW,
							new SpaceAtom(TeXLength.Unit.EM, -0.180),
							Symbols.RIGHTARROW);
					ra.setShape(true);
					return new TypedAtom(TeXConstants.TYPE_RELATION, ra);
				}
			};
		case "Longleftrightarrow":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(3);
					ra.add(Symbols.BIG_LEFTARROW,
							new SpaceAtom(TeXLength.Unit.EM, -0.180),
							Symbols.BIG_RIGHTARROW);
					ra.setShape(true);
					return new TypedAtom(TeXConstants.TYPE_RELATION, ra);
				}
			};

		case "nbsp":
		case "nobreaskspace":
		case "space":
		case " ":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom();
				}
			};

		case "{":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return Symbols.LBRACE;
				}
			};
		case "}":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return Symbols.RBRACE;
				}
			};
		case "nolimits":
			return new Command0AImpl() {
				@Override
				public boolean init(TeXParser tp) {
					final Atom a = tp.getLastAtom();
					if (a != null) {
						tp.addToConsumer(
								a.changeLimits(TeXConstants.SCRIPT_NOLIMITS));
					}
					return false;
				}
			};
		case "limits":
			return new Command0AImpl() {
				@Override
				public boolean init(TeXParser tp) {
					final Atom a = tp.getLastAtom();
					if (a != null) {
						tp.addToConsumer(
								a.changeLimits(TeXConstants.SCRIPT_LIMITS));
					}
					return false;
				}
			};
		case "normal":
			return new Command0AImpl() {
				@Override
				public boolean init(TeXParser tp) {
					final Atom a = tp.getLastAtom();
					if (a != null) {
						tp.addToConsumer(
								a.changeLimits(TeXConstants.SCRIPT_NORMAL));
					}
					return false;
				}
			};
		case "surd":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new VCenteredAtom(SymbolAtom.get("surdsign"));
				}
			};
		case "vcenter":
			return new CommandVCenter();

		case "int":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return Symbols.INT;
				}
			};
		case "intop":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return Symbols.INTOP;
				}
			};
		case "oint":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return Symbols.OINT;
				}
			};
		case "iint":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom integral = Symbols.INT;
					final SpaceAtom six = new SpaceAtom(TeXLength.Unit.MU, -6.,
							0., 0.);
					final SpaceAtom nine = new SpaceAtom(TeXLength.Unit.MU, -9.,
							0., 0.);
					final Atom choice = new MathchoiceAtom(nine, six, six, six);
					final RowAtom ra = new RowAtom(integral, choice, integral);
					ra.lookAtLastAtom = true;
					return ra.changeType(TeXConstants.TYPE_BIG_OPERATOR);
				}
			};
		case "iiint":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom integral = Symbols.INT;
					final SpaceAtom six = new SpaceAtom(TeXLength.Unit.MU, -6.,
							0., 0.);
					final SpaceAtom nine = new SpaceAtom(TeXLength.Unit.MU, -9.,
							0., 0.);
					final Atom choice = new MathchoiceAtom(nine, six, six, six);
					final RowAtom ra = new RowAtom(integral, choice, integral,
							choice, integral);
					ra.lookAtLastAtom = true;
					return ra.changeType(TeXConstants.TYPE_BIG_OPERATOR);
				}
			};
		case "iiiint":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom integral = Symbols.INT;
					final SpaceAtom six = new SpaceAtom(TeXLength.Unit.MU, -6.,
							0., 0.);
					final SpaceAtom nine = new SpaceAtom(TeXLength.Unit.MU, -9.,
							0., 0.);
					final Atom choice = new MathchoiceAtom(nine, six, six, six);
					final RowAtom ra = new RowAtom(integral, choice, integral,
							choice, integral, choice, integral);
					ra.lookAtLastAtom = true;
					return ra.changeType(TeXConstants.TYPE_BIG_OPERATOR);
				}
			};
		case "idotsint":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom integral = Symbols.INT;
					final Atom cdotp = Symbols.CDOTP;
					final SpaceAtom sa = new SpaceAtom(TeXLength.Unit.MU, -1.,
							0., 0.);
					final RowAtom cdots = new RowAtom(cdotp, cdotp, cdotp);
					final RowAtom ra = new RowAtom(integral, sa,
							cdots.changeType(TeXConstants.TYPE_INNER), sa,
							integral);
					ra.lookAtLastAtom = true;
					return ra.changeType(TeXConstants.TYPE_BIG_OPERATOR);
				}
			};
		case "lmoustache":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom at = new BigDelimiterAtom(
							SymbolAtom.get("lmoustache"), 1);
					at.setType(TeXConstants.TYPE_OPENING);
					return at;
				}
			};
		case "rmoustache":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom at = new BigDelimiterAtom(
							SymbolAtom.get("rmoustache"), 1);
					at.setType(TeXConstants.TYPE_CLOSING);
					return at;
				}
			};
		case "-":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return BreakMarkAtom.get();
				}
			};
		case "frac":
			return new CommandFrac();

		case "genfrac":
			return new CommandGenfrac();

		case "dfrac":
			return new CommandDFrac();

		case "tfrac":
			return new CommandTFrac();
		case "dbinom":
			return new CommandDBinom();

		case "tbinom":
			return new CommandTBinom();

		case "binom":
			return new CommandBinom();

		case "over":
			return new CommandOver();

		case "buildrel":
			return new CommandBuildRel();

		case "choose":
			return new CommandChoose(Symbols.LBRACK, Symbols.RBRACK);
		case "brace":
			return new CommandChoose(Symbols.LBRACE, Symbols.RBRACE);
		case "bangle":
			return new CommandChoose(Symbols.LANGLE, Symbols.RANGLE);
		case "brack":
			return new CommandChoose(Symbols.LSQBRACK, Symbols.RSQBRACK);

		case "overwithdelims":
			return new CommandOverwithdelims();
		case "atopwithdelims":
			return new CommandATopwithdelims();
		case "abovewithdelims":
			return new CommandAbovewithdelims();

		case "above":
			return new CommandAbove();
		case "atop":
			return new CommandATop();
		case "sqrt":
			return new CommandSqrt();

		case "fcscore":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final int arg = tp.getArgAsPositiveInteger();
					return FcscoreAtom.get(arg);
				}
			};
		case "longdiv":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final long dividend = tp.getArgAsPositiveInteger();
					final long divisor = tp.getArgAsPositiveInteger();
					return new LongdivAtom(divisor, dividend, tp);
				}
			};
		case "st":
			return new CommandSt();

		case "includegraphics":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final Map<String, String> arg1 = tp.getOptionAsMap();
					final String arg2 = tp.getArgAsString();
					return new GraphicsAtom(arg2, arg1);
				}
			};

		case "mathclap":
		case "clap":
			return new CommandClap();

		case "mathrlap":
		case "rlap":
			return new CommandRLap();

		case "mathllap":
		case "llap":
			return new CommandLLap();

		case "begin":
			return new CommandBE.Begin();
		case "end":
			return new CommandBE.End();
		case "begin@array":
			return new EnvArray.Begin("array", ArrayAtom.ARRAY);
		case "end@array":
			return new EnvArray.End("array");
		case "begin@tabular":
			return new EnvArray.Begin("tabular", ArrayAtom.ARRAY);
		case "end@tabular":
			return new EnvArray.End("tabular");
		case "\\":
			return new CommandCr("\\");
		case "begin@eqnarray":
			return new EnvArray.Begin("eqnarray", ArrayAtom.ARRAY,
					new ArrayOptions(3).addAlignment(TeXConstants.Align.RIGHT)
							.addAlignment(TeXConstants.Align.CENTER)
							.addAlignment(TeXConstants.Align.LEFT).close());
		case "end@eqnarray":
			return new EnvArray.End("eqnarray");
		case "begin@split":
			return new EnvArray.Begin("split", ArrayAtom.ARRAY,
					new ArrayOptions(2).addAlignment(TeXConstants.Align.RIGHT)
							.addAlignment(TeXConstants.Align.LEFT).close());
		case "end@split":
			return new EnvArray.End("split");
		case "begin@cases":
			return new EnvArray.Begin("cases", ArrayAtom.ARRAY,
					new ArrayOptions(3).addAlignment(TeXConstants.Align.LEFT)
							.addSeparator(
									new SpaceAtom(TeXConstants.Muskip.NEGTHIN))
							.addAlignment(TeXConstants.Align.LEFT).close());
		case "end@cases":
			return new EnvArray.End("cases");

		case "matrix":
		case "array":
			return new CommandMatrix();

		case "ooalign":
			return new CommandOoalign();
		case "pmatrix":
			return new CommandPMatrix();

		case "begin@matrix":
			return new EnvArray.Begin("matrix", ArrayAtom.MATRIX,
					ArrayOptions.getEmpty());
		case "end@matrix":
			return new EnvArray.End("matrix");
		case "begin@smallmatrix":
			return new EnvArray.Begin("smallmatrix", ArrayAtom.SMALLMATRIX,
					ArrayOptions.getEmpty());
		case "end@smallmatrix":
			return new EnvArray.End("smallmatrix");
		case "begin@align":
			return new EnvArray.Begin("align", ArrayAtom.ALIGN,
					ArrayOptions.getEmpty());
		case "end@align":
			return new EnvArray.End("align");
		case "begin@aligned":
			return new EnvArray.Begin("aligned", ArrayAtom.ALIGNED,
					ArrayOptions.getEmpty());
		case "end@aligned":
			return new EnvArray.End("aligned");
		case "begin@flalign":
			return new EnvArray.Begin("flalign", ArrayAtom.FLALIGN,
					ArrayOptions.getEmpty());
		case "end@flalign":
			return new EnvArray.End("flalign");
		case "begin@alignat":
			return new EnvArray.Begin("alignat", ArrayAtom.ALIGNAT,
					ArrayOptions.getEmpty());
		case "end@alignat":
			return new EnvArray.End("alignat");
		case "begin@alignedat":
			return new EnvArray.Begin("alignedat", ArrayAtom.ALIGNEDAT,
					ArrayOptions.getEmpty());
		case "end@alignedat":
			return new EnvArray.End("alignedat");
		case "begin@multline":
			return new EnvArray.Begin("multline", -1, ArrayOptions.getEmpty());
		case "end@multline":
			return new EnvArray.End("multline");
		case "begin@subarray":
			return new EnvArray.Begin("subarray", -1);
		case "end@subarray":
			return new EnvArray.End("subarray");
		case "substack":
			return new CommandSubstack();
		case "displaylines":
			return new CommandDisplaylines();
		case "begin@gather":
			return new EnvArray.Begin("gather", -1, ArrayOptions.getEmpty());
		case "end@gather":
			return new EnvArray.End("gather");
		case "begin@gathered":
			return new EnvArray.Begin("gathered", -1, ArrayOptions.getEmpty());
		case "end@gathered":
			return new EnvArray.End("gathered");
		case "begin@pmatrix":
			return new EnvArray.Begin("pmatrix", ArrayAtom.MATRIX,
					ArrayOptions.getEmpty());
		case "end@pmatrix":
			return new EnvArray.End("pmatrix", "lbrack", "rbrack");
		case "begin@bmatrix":
			return new EnvArray.Begin("bmatrix", ArrayAtom.MATRIX,
					ArrayOptions.getEmpty());
		case "end@bmatrix":
			return new EnvArray.End("bmatrix", "lsqbrack", "rsqbrack");
		case "begin@vmatrix":
			return new EnvArray.Begin("bmatrix", ArrayAtom.MATRIX,
					ArrayOptions.getEmpty());
		case "end@vmatrix":
			return new EnvArray.End("bmatrix", "vert");
		case "begin@Vmatrix":
			return new EnvArray.Begin("Vmatrix", ArrayAtom.MATRIX,
					ArrayOptions.getEmpty());
		case "end@Vmatrix":
			return new EnvArray.End("Vmatrix", "Vert");

		case "thinspace":
		case ",":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXConstants.Muskip.THIN);
				}
			};

		case "medspace":
		case ">":
		case ":":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXConstants.Muskip.MED);
				}
			};

		case "thickspace":
		case ";":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXConstants.Muskip.THICK);
				}
			};

		case "negthinspace":
		case "!":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXConstants.Muskip.NEGTHIN);
				}
			};

		case "negmedspace":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXConstants.Muskip.NEGMED);
				}
			};
		case "negthickspace":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXConstants.Muskip.NEGTHICK);
				}
			};
		case "enspace":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXLength.Unit.EM, 0.5, 0., 0.);
				}
			};
		case "enskip":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXLength.Unit.EM, 0.5, 0., 0.);
				}
			};
		case "quad":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXLength.Unit.EM, 1., 0., 0.);
				}
			};
		case "qquad":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXLength.Unit.EM, 2., 0., 0.);
				}
			};
		case "Space":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXLength.Unit.EM, 3., 0., 0.);
				}
			};
		case "textcircled":
			return new CommandTextCircled();

		case "romannumeral":
			return new CommandRomNum(false);
		case "Romannumeral":
			return new CommandRomNum(true);
		case "T":
			return new CommandT();

		case "char":
			return new Command0AImpl() {
				@Override
				public boolean init(TeXParser tp) {
					final int c = tp.getArgAsCharFromCode();
					if (c == 0) {
						throw new ParseException(tp,
								"Invalid character in \\char: 0.");
					}
					if (c <= 0xFFFF) {
						final char cc = (char) c;
						if ((cc >= '0' && cc <= '9') || (cc >= 'a' && cc <= 'z')
								|| (cc >= 'A' && cc <= 'Z')) {
							tp.convertASCIIChar(cc, true);
						} else {
							tp.convertCharacter(cc, true);
						}
					} else {
						tp.convertCharacter(c);
					}
					return false;
				}
			};
		case "unicode":
			return new CommandUnicode();
		case "kern":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(tp.getArgAsLength());
				}
			};
		case "Dstrok":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(
							new SpaceAtom(TeXLength.Unit.EX, -0.1, 0., 0.),
							Symbols.BAR);
					final VRowAtom vra = new VRowAtom(new LapedAtom(ra, 'r'));
					vra.setRaise(TeXLength.Unit.EX, -0.55);
					return new RowAtom(vra, new RomanAtom(
							new CharAtom('D', TextStyle.MATHNORMAL)));
				}
			};
		case "dstrok":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(
							new SpaceAtom(TeXLength.Unit.EX, 0.25, 0., 0.),
							Symbols.BAR);
					final VRowAtom vra = new VRowAtom(new LapedAtom(ra, 'r'));
					vra.setRaise(TeXLength.Unit.EX, -0.1);
					return new RowAtom(vra, new RomanAtom(
							new CharAtom('d', TextStyle.MATHNORMAL)));
				}
			};
		case "Hstrok":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(
							new SpaceAtom(TeXLength.Unit.EX, 0.28, 0., 0.),
							Symbols.TEXTENDASH);
					final VRowAtom vra = new VRowAtom(new LapedAtom(ra, 'r'));
					vra.setRaise(TeXLength.Unit.EX, 0.55);
					return new RowAtom(vra, new RomanAtom(
							new CharAtom('H', TextStyle.MATHNORMAL)));
				}
			};
		case "hstrok":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final RowAtom ra = new RowAtom(
							new SpaceAtom(TeXLength.Unit.EX, -0.1, 0., 0.),
							Symbols.BAR);
					final VRowAtom vra = new VRowAtom(new LapedAtom(ra, 'r'));
					vra.setRaise(TeXLength.Unit.EX, -0.1);
					return new RowAtom(vra, new RomanAtom(
							new CharAtom('h', TextStyle.MATHNORMAL)));
				}
			};
		case "smallfrowneq":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom at = new UnderOverAtom(Symbols.EQUALS,
							Symbols.SMALLFROWN,
							new TeXLength(TeXLength.Unit.MU, -2.), true, true);
					return at.changeType(TeXConstants.TYPE_RELATION);
				}
			};
		case "frowneq":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom at = new UnderOverAtom(Symbols.EQUALS,
							Symbols.FROWN, TeXLength.getZero(), true, true);
					return at.changeType(TeXConstants.TYPE_RELATION);
				}
			};
		case "coloncolonapprox":
			return new CommandColonFoo.ColonColonFoo("approx");
		case "colonapprox":
			return new CommandColonFoo.ColonFoo("approx");
		case "coloncolonsim":
			return new CommandColonFoo.ColonColonFoo("sim");
		case "colonsim":
			return new CommandColonFoo.ColonFoo("sim");
		case "coloncolon":
			return new CommandColonFoo.ColonColonFoo();
		case "coloncolonequals":
			return new CommandColonFoo.ColonColonFoo("equals");
		case "colonequals":
			return new CommandColonFoo.ColonFoo("equals");
		case "coloncolonminus":
			return new CommandColonFoo.ColonColonFoo("minus");
		case "colonminus":
			return new CommandColonFoo.ColonFoo("minus");
		case "equalscoloncolon":
			return new CommandColonFoo.FooColonColon("equals");
		case "equalscolon":
			return new CommandColonFoo.FooColon("equals");
		case "minuscoloncolon":
			return new CommandColonFoo.FooColonColon("minus");
		case "minuscolon":
			return new CommandColonFoo.FooColon("minus");
		case "simcoloncolon":
			return new CommandColonFoo.FooColonColon("sim");
		case "simcolon":
			return new CommandColonFoo.FooColon("sim");
		case "approxcoloncolon":
			return new CommandColonFoo.FooColonColon("approx");
		case "approxcolon":
			return new CommandColonFoo.FooColon("approx");
		case "geoprop":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final SymbolAtom nd = Symbols.NORMALDOT;
					final RowAtom ddot = new RowAtom(nd,
							new SpaceAtom(TeXLength.Unit.MU, 4., 0., 0.), nd);
					final TeXLength l = new TeXLength(TeXLength.Unit.MU, -3.4);
					Atom at = new UnderOverAtom(Symbols.MINUS, ddot, l, false,
							ddot, l, false);
					return at.changeType(TeXConstants.TYPE_RELATION);
				}
			};
		case "ratio":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom a = new VCenteredAtom(Symbols.COLON
							.changeType(TeXConstants.TYPE_ORDINARY));
					return a.changeType(TeXConstants.TYPE_RELATION);
				}
			};
		case "dotminus":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom at = new UnderOverAtom(Symbols.MINUS,
							Symbols.NORMALDOT,
							new TeXLength(TeXLength.Unit.EX, -0.4), false,
							true);
					return at.changeType(TeXConstants.TYPE_BINARY_OPERATOR);
				}
			};
		case "tiny":
			return new CommandTiny1();

		case "Tiny":
			return new CommandTiny2();

		case "scriptsize":
			return new CommandScriptSize();

		case "footnotesize":
			return new CommandFootnoteSize();

		case "small":
			return new CommandSmall();

		case "normalsize":
			return new CommandNormalSize();

		case "large":
			return new CommandLarge();

		case "Large":
			return new CommandLarge2();

		case "LARGE":
			return new CommandLarge3();

		case "huge":
			return new CommandHuge1();

		case "Huge":
			return new CommandHuge2();

		case "sc":
			return new CommandSc();

		case "hline":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					if (!tp.isArrayMode()) {
						throw new ParseException(tp,
								"The macro \\hline is only available in array mode !");
					}
					return new HlineAtom();
				}
			};
		case "cellcolor":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					if (!tp.isArrayMode()) {
						throw new ParseException(tp,
								"The macro \\cellcolor is only available in array mode !");
					}
					final Color c = CommandDefinecolor.getColor(tp);
					return new EnvArray.CellColor(c);
				}
			};
		case "rowcolor":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					if (!tp.isArrayMode()) {
						throw new ParseException(tp,
								"The macro \\rowcolor is only available in array mode !");
					}
					final Color c = CommandDefinecolor.getColor(tp);
					return new EnvArray.RowColor(c);
				}
			};
		case "jlmText":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final String arg = tp.getGroupAsArgument();
					return new JavaFontRenderingAtom(arg, Font.PLAIN);
				}
			};
		case "jlmTextit":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final String arg = tp.getGroupAsArgument();
					return new JavaFontRenderingAtom(arg, Font.ITALIC);
				}
			};
		case "jlmTextbf":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final String arg = tp.getGroupAsArgument();
					return new JavaFontRenderingAtom(arg, Font.BOLD);
				}
			};
		case "jlmTextitbf":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final String arg = tp.getGroupAsArgument();
					return new JavaFontRenderingAtom(arg,
							Font.BOLD | Font.ITALIC);
				}
			};
		case "jlmExternalFont":
			return new Command0AImpl() {
				@Override
				public boolean init(TeXParser tp) {
					final String fontname = tp.getArgAsString();
					JavaFontRenderingBox.setFont(fontname);
					return false;
				}
			};
		// XXX
		// case "jlmDynamic":
		// return new Command0AImpl() {
		// @Override
		// public boolean init(TeXParser tp) {
		// if (DynamicAtom.hasAnExternalConverterFactory()) {
		// final char opt = tp.getOptionAsChar();
		// final String arg = tp.getGroupAsArgument();
		// tp.addToConsumer(new DynamicAtom(arg, opt));
		//
		// return false;
		// }
		// throw new ParseException(tp,
		// "No ExternalConverterFactory set !");
		// }
		// };
		case "doteq":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom at = new UnderOverAtom(Symbols.EQUALS,
							Symbols.LDOTP,
							new TeXLength(TeXLength.Unit.MU, 3.7), false, true);
					return at.changeType(TeXConstants.TYPE_RELATION);
				}
			};
		case "cong":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final VRowAtom vra = new VRowAtom(Symbols.SIM,
							new SpaceAtom(TeXLength.Unit.MU, 0., 1.5, 0.),
							Symbols.EQUALS);
					vra.setRaise(TeXLength.Unit.MU, -1.);
					return vra.changeType(TeXConstants.TYPE_RELATION);
				}
			};
		case "fbox":
			return new CommandFBox();

		case "boxed":
			return new CommandBoxed();

		case "dbox":
			return new CommandDBox();

		case "fcolorbox":
			return new CommandFColorBox();

		case "colorbox":
			return new CommandColorBox();

		case "fgcolor":
		case "textcolor":
			return new CommandTextColor();

		case "color":
			return new CommandColor();

		case "bgcolor":
			return new CommandBGColor();

		case "definecolor":
			return new CommandDefinecolor();

		case "doublebox":
			return new CommandDoubleBox();

		case "ovalbox":
			return new CommandOvalBox();

		case "cornersize":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final double cs = tp.getArgAsDecimal();
					return new SetLengthAtom(
							new TeXLength(TeXLength.Unit.NONE, cs),
							"cornersize");
				}
			};

		case "shadowbox":
			return new CommandShadowBox();

		case "raisebox":
			return new CommandRaiseBox();

		case "raise":
			return new CommandRaise();

		case "lower":
			return new CommandLower();

		case "moveleft":
			return new CommandMoveLeft();

		case "moveright":
			return new CommandMoveRight();

		case "resizebox":
			return new CommandResizeBox();

		case "scalebox":
			return new CommandScaleBox();

		case "reflectbox":
			return new CommandReflectBox();

		case "rotatebox":
			return new CommandRotateBox();

		case "scriptscriptstyle":
			return new CommandScriptScriptStyle();

		case "textstyle":
			return new CommandTextStyle2();

		case "scriptstyle":
			return new CommandScriptStyle();

		case "displaystyle":
			return new CommandDisplayStyle();

		case "Biggr":
			return new CommandBigr(TeXConstants.TYPE_CLOSING, 4);
		case "biggr":
			return new CommandBigr(TeXConstants.TYPE_CLOSING, 3);
		case "Bigr":
			return new CommandBigr(TeXConstants.TYPE_CLOSING, 2);
		case "bigr":
			return new CommandBigr(TeXConstants.TYPE_CLOSING, 1);
		case "Biggl":
			return new CommandBigr(TeXConstants.TYPE_OPENING, 4);
		case "biggl":
			return new CommandBigr(TeXConstants.TYPE_OPENING, 3);
		case "Bigl":
			return new CommandBigr(TeXConstants.TYPE_OPENING, 2);
		case "bigl":
			return new CommandBigr(TeXConstants.TYPE_OPENING, 1);
		case "Biggm":
			return new CommandBigr(TeXConstants.TYPE_RELATION, 4);
		case "biggm":
			return new CommandBigr(TeXConstants.TYPE_RELATION, 3);
		case "Bigm":
			return new CommandBigr(TeXConstants.TYPE_RELATION, 2);
		case "bigm":
			return new CommandBigr(TeXConstants.TYPE_RELATION, 1);
		case "Bigg":
			return new CommandBigg(4);
		case "bigg":
			return new CommandBigg(3);
		case "Big":
			return new CommandBigg(2);
		case "big":
			return new CommandBigg(1);
		case "mathstrut":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new PhantomAtom(
							Symbols.LBRACK
									.changeType(TeXConstants.TYPE_ORDINARY),
							false, true, true);
				}
			};
		case "phantom":
			return new CommandPhantom();

		case "vphantom":
			return new CommandVPhantom();

		case "hphantom":
			return new CommandHPhantom();

		case "LaTeX":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new LaTeXAtom();
				}
			};
		case "mathcal":
			return new CommandTextStyle(TextStyle.MATHCAL);
		case "cal":
			return new CommandTextStyleTeX(TextStyle.MATHCAL);
		case "mathfrak":
			return new CommandTextStyle(TextStyle.MATHFRAK);
		case "frak":
			return new CommandTextStyleTeX(TextStyle.MATHFRAK);
		case "mathbb":
			return new CommandTextStyle(TextStyle.MATHBB);
		case "Bbb":
			return new CommandTextStyleTeX(TextStyle.MATHBB);
		case "mathscr":
			return new CommandTextStyle(TextStyle.MATHSCR);
		case "scr":
			return new CommandTextStyleTeX(TextStyle.MATHSCR);
		case "mathds":
			return new CommandTextStyle(TextStyle.MATHDS);
		case "oldstylenums":
			return new CommandTextStyle(TextStyle.OLDSTYLENUMS);

		case "mathsf":
			return new CommandMathSf();

		case "sf":
			return new CommandSf();

		case "mathrm":
			return new CommandMathRm();

		case "rm":
			return new CommandRm();

		case "mit":
		case "mathit":
			return new CommandMathIt();

		case "it":
			return new CommandIt();

		case "mathtt":
			return new CommandMathTt();

		case "tt":
			return new CommandTt();

		case "mathbf":
			return new CommandMathBf();

		case "bf":
			return new CommandBf();

		case "boldsymbol":
		case "bold":
			return new CommandBold();

		case "undertilde":
			return new CommandUnderTilde();

		case "b":
			return new CommandB();

		case "underaccent":
			return new CommandUnderAccent();

		case "accentset":
			return new CommandAccentSet();

		case "underset":
			return new CommandUnderSet();

		case "overset":
			return new CommandOverSet();

		case "stackbin":
			return new CommandStackBin();

		case "stackrel":
			return new CommandStackRel();

		case "questeq":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom at = new UnderOverAtom(Symbols.EQUALS,
							Symbols.QUESTION,
							new TeXLength(TeXLength.Unit.MU, 2.5), true, true);
					return at.changeType(TeXConstants.TYPE_RELATION);
				}
			};
		case "eqdef":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new BuildrelAtom(Symbols.EQUALS, new RomanAtom(
							TeXParser.getAtomForLatinStr("def", true)));
				}
			};
		case "shoveleft":
			return new CommandShoveLeft();
		case "shoveright":
			return new CommandShoveRight();

		case "hdotsfor":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					if (!tp.isArrayMode()) {
						throw new ParseException(tp,
								"The macro \\hdotsfor is only available in array mode !");
					}
					final double x = tp.getOptionAsDecimal(1.);
					final int n = tp.getArgAsPositiveInteger();
					if (n == -1) {
						throw new ParseException(tp,
								"The macro \\hdotsfor requires a positive integer");
					}
					return new HdotsforAtom(n, x);
				}
			};
		case "multicolumn":
			return new CommandMulticolumn();

		case "intertext":
			return new CommandInterText();

		case "cr":
			return new CommandCr("cr");
		case "newline":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					tp.close();
					if (tp.isArrayMode()) {
						return EnvArray.RowSep.get();
					}
					throw new ParseException(tp,
							"The macro \\newline must be used in an array");
				}
			};
		case "begin@math":
			return new CommandMathStyles.OpenBracket(
					TeXConstants.Opener.BEGIN_MATH);
		case "end@math":
			return new CommandMathStyles.CloseBracket(
					TeXConstants.Opener.BEGIN_MATH, TeXConstants.STYLE_TEXT,
					"The command \\) doesn't match any \\(");
		case "[":
			return new CommandMathStyles.OpenBracket(
					TeXConstants.Opener.B_LSQBRACKET);
		case "]":
			return new CommandMathStyles.CloseBracket(
					TeXConstants.Opener.B_LSQBRACKET,
					TeXConstants.STYLE_DISPLAY,
					"The command \\] doesn't match any \\[");

		case "displaymath":
			return new CommandDisplayMath();

		case "(":
			return new CommandMathStyles.OpenBracket(
					TeXConstants.Opener.B_LBRACKET);
		case ")":
			return new CommandMathStyles.CloseBracket(
					TeXConstants.Opener.B_LBRACKET, TeXConstants.STYLE_TEXT,
					"The command \\) doesn't match any \\(");

		case "math":
			return new CommandMath();

		case "iddots":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new IddotsAtom();
				}
			};
		case "ddots":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new DdotsAtom();
				}
			};
		case "vdots":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new VdotsAtom();
				}
			};
		case "smash":
			return new CommandSmash();

		case "joinrel":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXLength.Unit.MU, -3, 0, 0)
							.changeType(TeXConstants.TYPE_RELATION);
				}
			};
		case "mathclose":
			return new CommandMathClose();

		case "mathopen":
			return new CommandMathOpen();

		case "mathbin":
			return new CommandMathBin();

		case "mathinner":
			return new CommandMathInner();

		case "mathord":
			return new CommandMathOrd();

		case "mathpunct":
			return new CommandMathPunct();

		case "mathop":
			return new CommandMathOp();

		case "mathrel":
			return new CommandMathRel();

		case "underline":
			return new CommandUnderline();

		case "overline":
			return new CommandOverline();

		case "overparen":
			return new CommandOverParen();

		case "underparen":
			return new CommandUnderParen();

		case "overbrack":
			return new CommandOverBrack();

		case "underbrack":
			return new CommandUnderBrack();

		case "overbrace":
			return new CommandOverBrace();

		case "underbrace":
			return new CommandUnderBrace();

		case "prescript":
			return new CommandPreScript();

		case "sideset":
			return new CommandSideSet();

		case "xmapsto":
			return new CommandXMapsTo();

		case "xlongequal":
			return new CommandXLongEqual();

		case "xrightarrow":
			return new CommandXRightArrow();

		case "xleftarrow":
			return new CommandXLeftArrow();

		case "xhookleftarrow":
			return new CommandXHookLeftArrow();

		case "xhookrightarrow":
			return new CommandXHookRightArrow();

		case "xleftrightarrow":
			return new CommandXLeftRightArrow();

		case "xrightharpoondown":
			return new CommandXRightHarpoonDown();

		case "xrightharpoonup":
			return new CommandXRightHarpoonUp();

		case "xleftharpoondown":
			return new CommandXLeftHarpoonDown();

		case "xleftharpoonup":
			return new CommandXLeftHarpoonUp();

		case "xleftrightharpoons":
			return new CommandXLeftRightHarpoons();

		case "xrightleftharpoons":
			return new CommandXRightLeftHarpoons();

		case "xrightsmallleftharpoons":
			return new CommandXRightSmallLeftHarpoons();

		case "xsmallrightleftharpoons":
			return new CommandXSmallRightLeftHarpoons();

		case "xleftrightarrows":
			return new CommandXLeftRightArrows();

		case "xrightleftarrows":
			return new CommandXRightLeftArrows();

		case "underleftrightarrow":
			return new CommandUnderLeftRightArrow();

		case "underleftarrow":
			return new CommandUnderLeftArrow();

		case "underrightarrow":
			return new CommandUnderRightArrow();

		case "overleftrightarrow":
			return new CommandOverLeftRightArrow();

		case "overleftarrow":
			return new CommandOverLeftArrow();

		case "overrightarrow":
			return new CommandOverRightArrow();

		case "ogonek":
			return new CommandOgonek();
		case "k":
			return new CommandOgonek();

		case "tcaron":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new tcaronAtom();
				}
			};
		case "Lcaron":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new LCaronAtom(true);
				}
			};
		case "lcaron":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new LCaronAtom(false);
				}
			};
		case "Tstroke":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new TStrokeAtom(true);
				}
			};
		case "tstroke":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new TStrokeAtom(false);
				}
			};
		case "IJ":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new IJAtom(true);
				}
			};
		case "ij":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new IJAtom(false);
				}
			};
		case "cedilla":
			return new CommandCedilla();
		case "c":
			return new CommandCedilla();

		case "~":
			return new CommandTilde1();

		case "tilde":
			return new CommandTilde2();

		case "widetilde":
			return new CommandWideTilde();

		case "'":
			return new CommandAcute1();

		case "acute":
			return new CommandAcute2();

		case "skew":
			return new CommandSkew();

		case "^":
			return new CommandHat1();

		case "hat":
			return new CommandHat2();

		case "widehat":
			return new CommandWideHat();

		case "\"":
			return new CommandQuotes();

		case "ddot":
			return new CommandDDot();

		case "dddot":
			return new CommandDDDot();

		case "ddddot":
			return new CommandDDDDot();

		case "`":
			return new CommandGrave1();

		case "grave":
			return new CommandGrave2();

		case "=":
			return new CommandEquals();

		case "bar":
			return new CommandBar();

		case ".":
			return new CommandDot1();

		case "dot":
			return new CommandDot2();

		case "cyrddot":
			return new CommandCyrDDot();

		case "u":
			return new CommandBreve1();

		case "breve":
			return new CommandBreve2();

		case "v":
			return new CommandCheck();

		case "check":
			return new CommandMap();

		case "H":
			return new CommandH();

		case "t":
			return new CommandT2();

		case "r":
			return new CommandR();

		case "mathring":
			return new CommandMathRing();

		case "U":
			return new CommandU();

		case "vec":
			return new CommandVec();

		case "accent":
			return new CommandAccent();

		case "grkaccent":
			return new CommandGrkAccent();

		case "_":
		case "underscore":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new UnderscoreAtom();
				}
			};

		case "mbox":
			return new CommandMBox();

		case "textsuperscript":
			return new CommandTextSuperscript();

		case "textsubscript":
			return new CommandTextSubscript();

		case "text":
			return new CommandText2();

		case "pmb":
			return new CommandPMB();

		case "textbf":
			return new CommandTextBf();

		case "textit":
			return new CommandTextIt();

		case "textrm":
			return new CommandTextRm();

		case "textsf":
			return new CommandTextSf();

		case "texttt":
			return new CommandTextTt();

		case "textsc":
			return new CommandTextSc();

		case "operatorname":
			return new CommandOperatorName();

		case "sfrac":
			return new CommandSfrac();

		case "cfrac":
			return new CommandCFrac();

		case "the":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final String name = tp.getArgAsCommand(true);
					return new TheAtom(name);
				}
			};

		case "setlength":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final String name = tp.getArgAsCommand(true);
					TeXLength newLength = tp.getArgAsLength();
					if (newLength == null) {
						throw new ParseException(tp,
								"Invalid length in \\setlength");
					}
					return new SetLengthAtom(newLength, name);
				}
			};
		case "rule":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					TeXLength r = tp.getOptionAsLength(TeXLength.getZero());
					if (r == null) {
						r = new TeXLength();
					}
					TeXLength w = tp.getArgAsLength();
					if (w == null) {
						throw new ParseException(tp,
								"Invalid length in \\rule");
					}
					TeXLength h = tp.getArgAsLength();
					if (h == null) {
						throw new ParseException(tp,
								"Invalid length in \\rule");
					}
					return new RuleAtom(w, h, r);
				}
			};
		case "vrule":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final TeXLength[] lengths = tp.getDimensions();
					return new HVruleAtom(lengths[0], lengths[1], lengths[2],
							false);
				}
			};
		case "hrule":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final TeXLength[] lengths = tp.getDimensions();
					return new HVruleAtom(lengths[0], lengths[1], lengths[2],
							true);
				}
			};
		case "textvisiblespace":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					tp.skipPureWhites();
					final Atom a = new HVruleAtom(null,
							new TeXLength(TeXLength.Unit.EX, 0.3), null, false);
					return new RowAtom(new SpaceAtom(TeXLength.Unit.EM, 0.06),
							a,
							new HVruleAtom(
									new TeXLength(TeXLength.Unit.EM, 0.3), null,
									null, true),
							a);
				}
			};
		case "hspace":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final TeXLength w = tp.getArgAsLength();
					if (w == null) {
						throw new ParseException(tp,
								"Invalid length in \\hspace");
					}
					return new SpaceAtom(w.getUnit(), w.getL(), 0., 0.);
				}
			};
		case "vspace":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final TeXLength h = tp.getArgAsLength();
					if (h == null) {
						throw new ParseException(tp,
								"Invalid length in \\vspace");
					}
					return new SpaceAtom(h.getUnit(), 0., h.getL(), 0.);
				}
			};
		case "degree":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					return SubSupCom.get(SubSupCom.getBase(tp), null,
							Symbols.CIRC);
				}
			};
		case "sphat":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a;
					double raise;
					if (tp.isMathMode()) {
						a = Symbols.WIDEHAT;
						raise = 1.1;
					} else {
						a = Symbols.HAT;
						raise = 0.8;
					}
					a = new StyleAtom(TeXConstants.STYLE_DISPLAY, a);
					final VRowAtom vra = new VRowAtom(a);
					vra.setRaise(TeXLength.Unit.EX, raise);
					a = new SmashedAtom(vra);
					return SubSupCom.get(SubSupCom.getBase(tp), null, a);
				}
			};
		case "spbreve":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom ex = new SpaceAtom(TeXConstants.Muskip.NEGTHIN);
					Atom a = Symbols.BREVE;
					a = new StyleAtom(TeXConstants.STYLE_DISPLAY, a);
					final VRowAtom vra = new VRowAtom(a);
					vra.setRaise(TeXLength.Unit.EX, 1.1);
					a = new SmashedAtom(vra);
					final RowAtom ra = new RowAtom(ex, a);
					return
					SubSupCom.get(SubSupCom.getBase(tp), null, ra);
				}
			};
		case "spcheck":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom a = Symbols.VEE;
					return SubSupCom.get(SubSupCom.getBase(tp), null, a);
				}
			};
		case "sptilde":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom a = Symbols.SIM;
					return SubSupCom.get(SubSupCom.getBase(tp), null, a);
				}
			};
		case "spdot":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = Symbols.NORMALDOT;
					a = new StyleAtom(TeXConstants.STYLE_DISPLAY, a);
					final VRowAtom vra = new VRowAtom(a);
					vra.setRaise(TeXLength.Unit.EX, 0.8);
					a = new SmashedAtom(vra);
					return SubSupCom.get(SubSupCom.getBase(tp), null, a);
				}
			};
		case "spddot":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = Symbols.NORMALDOT;
					final RowAtom ra = new RowAtom(a, a);
					a = new StyleAtom(TeXConstants.STYLE_DISPLAY, ra);
					final VRowAtom vra = new VRowAtom(a);
					vra.setRaise(TeXLength.Unit.EX, 0.8);
					a = new SmashedAtom(vra);
					return SubSupCom.get(SubSupCom.getBase(tp), null, a);
				}
			};
		case "spdddot":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = Symbols.NORMALDOT;
					final RowAtom ra = new RowAtom(a, a, a);
					a = new StyleAtom(TeXConstants.STYLE_DISPLAY, ra);
					final VRowAtom vra = new VRowAtom(a);
					vra.setRaise(TeXLength.Unit.EX, 0.8);
					a = new SmashedAtom(vra);
					return SubSupCom.get(SubSupCom.getBase(tp), null, a);
				}
			};
		case "log":
			return new CommandOpName("log", false);
		case "lg":
			return new CommandOpName("lg", false);
		case "ln":
			return new CommandOpName("ln", false);
		case "lim":
			return new CommandOpName("lim", true);
		case "sin":
			return new CommandOpName("sin", false);
		case "arcsin":
			return new CommandOpName("arcsin", false);
		case "sinh":
			return new CommandOpName("sinh", false);
		case "cos":
			return new CommandOpName("cos", false);
		case "arccos":
			return new CommandOpName("arccos", false);
		case "cosh":
			return new CommandOpName("cosh", false);
		case "cot":
			return new CommandOpName("cot", false);
		case "arccot":
			return new CommandOpName("arccot", false);
		case "coth":
			return new CommandOpName("coth", false);
		case "tan":
			return new CommandOpName("tan", false);
		case "arctan":
			return new CommandOpName("arctan", false);
		case "tanh":
			return new CommandOpName("tanh", false);
		case "sec":
			return new CommandOpName("sec", false);
		case "arcsec":
			return new CommandOpName("arcsec", false);
		case "sech":
			return new CommandOpName("sech", false);
		case "csc":
			return new CommandOpName("csc", false);
		case "arccsc":
			return new CommandOpName("arccsc", false);
		case "csch":
			return new CommandOpName("csch", false);
		case "arg":
			return new CommandOpName("arg", false);
		case "ker":
			return new CommandOpName("ker", false);
		case "dim":
			return new CommandOpName("dim", false);
		case "hom":
			return new CommandOpName("hom", false);
		case "exp":
			return new CommandOpName("exp", false);
		case "deg":
			return new CommandOpName("deg", false);
		case "max":
			return new CommandOpName("max", true);
		case "min":
			return new CommandOpName("min", true);
		case "sup":
			return new CommandOpName("sup", true);
		case "inf":
			return new CommandOpName("inf", true);
		case "det":
			return new CommandOpName("det", true);
		case "Pr":
			return new CommandOpName("Pr", true);
		case "gcd":
			return new CommandOpName("gcd", true);
		case "limsup":
			return new CommandOpName("lim", "sup", true);
		case "liminf":
			return new CommandOpName("lim", "inf", true);
		case "injlim":
			return new CommandOpName("inj", "lim", true);
		case "projlim":
			return new CommandOpName("proj", "lim", true);
		case "varinjlim":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = new RomanAtom(
							TeXParser.getAtomForLatinStr("lim", true));
					a = new UnderOverArrowAtom(a, false, false);
					a = a.changeType(TeXConstants.TYPE_BIG_OPERATOR);
					a.type_limits = TeXConstants.SCRIPT_LIMITS;
					return a;
				}
			};
		case "varprojlim":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = new RomanAtom(
							TeXParser.getAtomForLatinStr("lim", true));
					a = new UnderOverArrowAtom(a, true, false);
					a = a.changeType(TeXConstants.TYPE_BIG_OPERATOR);
					a.type_limits = TeXConstants.SCRIPT_LIMITS;
					return a;
				}
			};
		case "varliminf":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = new RomanAtom(
							TeXParser.getAtomForLatinStr("lim", true));
					a = new UnderlinedAtom(a);
					a = a.changeType(TeXConstants.TYPE_BIG_OPERATOR);
					a.type_limits = TeXConstants.SCRIPT_LIMITS;
					return a;
				}
			};
		case "varlimsup":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = new RomanAtom(
							TeXParser.getAtomForLatinStr("lim", true));
					a = new OverlinedAtom(a);
					a = a.changeType(TeXConstants.TYPE_BIG_OPERATOR);
					a.type_limits = TeXConstants.SCRIPT_LIMITS;
					return a;
				}
			};
		case "with":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return Symbols.WITH
							.changeType(TeXConstants.TYPE_BINARY_OPERATOR);
				}
			};
		case "parr":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = new RotateAtom(Symbols.WITH, 180.,
							new HashMap<String, String>() {
								{
									put("origin", "c");
								}
							});
					return a.changeType(TeXConstants.TYPE_BINARY_OPERATOR);
				}
			};
		case "copyright":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = new RomanAtom(new CharAtom('c', false));
					a = new RaiseAtom(a, new TeXLength(TeXLength.Unit.EX, 0.2),
							TeXLength.getZero(), TeXLength.getZero());
					return new TextCircledAtom(a);
				}
			};
		case "L":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom a = new RowAtom(SymbolAtom.get("polishlcross"),
							new CharAtom('L', false));
					return new RomanAtom(a);
				}
			};
		case "l":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom a = new RowAtom(SymbolAtom.get("polishlcross"),
							new CharAtom('l', false));
					return new RomanAtom(a);
				}
			};
		case "Join":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					Atom a = new LapedAtom(SymbolAtom.get("ltimes"), 'r');
					a = new RowAtom(a, SymbolAtom.get("rtimes"));
					a = a.changeType(TeXConstants.TYPE_BIG_OPERATOR);
					a.type_limits = TeXConstants.SCRIPT_NORMAL;
					return a;
				}
			};
		case "notin":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new RowAtom(Symbols.NOT, Symbols.IN);
				}
			};

		case "neq":
		case "ne":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new RowAtom(Symbols.NOT, Symbols.EQUALS);
				}
			};

		case "JLaTeXMath":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new RowAtom(
							new CharAtom('J', TextStyle.MATHBB, true),
							new LaTeXAtom(),
							new CharAtom('M', TextStyle.MATHNORMAL, true),
							new CharAtom('a', TextStyle.MATHNORMAL, true),
							new CharAtom('t', TextStyle.MATHNORMAL, true),
							new CharAtom('h', TextStyle.MATHNORMAL, true));
				}
			};

		case "dotsc":
		case "dots":
		case "dotso":
		case "ldots":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom ldotp = Symbols.LDOTP;
					return new RowAtom(ldotp, ldotp, ldotp)
							.changeType(TeXConstants.TYPE_INNER);
				}
			};

		case "dotsb":
		case "dotsm":
		case "cdots":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom cdotp = Symbols.CDOTP;
					return new RowAtom(cdotp, cdotp, cdotp)
							.changeType(TeXConstants.TYPE_INNER);
				}
			};

		case "dotsi":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					final Atom cdotp = Symbols.CDOTP;
					final RowAtom ra = new RowAtom(cdotp, cdotp, cdotp);
					return new RowAtom(
							new SpaceAtom(TeXConstants.Muskip.NEGTHIN),
							ra.changeType(TeXConstants.TYPE_INNER));
				}
			};
		case "relbar":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SmashedAtom(Symbols.MINUS)
							.changeType(TeXConstants.TYPE_RELATION);
				}
			};

		// case "kern": XXX
		case "mspace":
		case "hskip":
		case "mskip":
		case "mkern":
			return new Command0AImpl() {
				@Override
				public Atom newI(TeXParser tp) {
					final TeXLength len = tp.getArgAsLength();
					return new SpaceAtom(len);
				}
			};

		case "strut":
			return new Command0A() {
				@Override
				public Atom newI(TeXParser tp) {
					return new SpaceAtom(TeXLength.Unit.PT, 0., 8.6, 3.);
				}
			};
		case "iff":
			return new Replacement("\\mathrel{\\;\\Longleftrightarrow\\;}");
		case "bowtie":
			return new Replacement(
					"\\mathrel{\\mathrel{\\triangleright}\\joinrel\\mathrel{\\triangleleft}}");
		case "models":
			return new Replacement(
					"\\mathrel{\\mathrel{\\vert}\\joinrel\\equals}");
		case "implies":
			return new Replacement("\\mathrel{\\;\\Longrightarrow\\;}");
		case "impliedby":
			return new Replacement("\\mathrel{\\;\\Longleftarrow\\;}");
		case "mapsto":
			return new Replacement("\\mathrel{\\mapstochar\\rightarrow}");
		case "longmapsto":
			return new Replacement("\\mathrel{\\mapstochar\\longrightarrow}");
		case "Mapsto":
			return new Replacement("\\mathrel{\\Mapstochar\\Rightarrow}");
		case "mapsfrom":
			return new Replacement("\\mathrel{\\leftarrow\\mapsfromchar}");
		case "Mapsfrom":
			return new Replacement("\\mathrel{\\Leftarrow\\Mapsfromchar}");
		case "Longmapsto":
			return new Replacement("\\mathrel{\\Mapstochar\\Longrightarrow}");
		case "longmapsfrom":
			return new Replacement("\\mathrel{\\longleftarrow\\mapsfromchar}");
		case "Longmapsfrom":
			return new Replacement("\\mathrel{\\Longleftarrow\\Mapsfromchar}");
		case "arrowvert":
			return new Replacement("\\vert");
		case "Arrowvert":
			return new Replacement("\\Vert");
		case "aa":
			return new Replacement("\\mathring{a}");
		case "AA":
			return new Replacement("\\mathring{A}");
		case "ddag":
			return new Replacement("\\ddagger");
		case "dag":
			return new Replacement("\\dagger");
		case "Doteq":
			return new Replacement("\\doteqdot");
		case "doublecup":
			return new Replacement("\\Cup");
		case "doublecap":
			return new Replacement("\\Cap");
		case "llless":
			return new Replacement("\\lll");
		case "gggtr":
			return new Replacement("\\ggg");
		case "Alpha":
			return new Replacement("\\mathord{\\mathrm{A}}");
		case "Beta":
			return new Replacement("\\mathord{\\mathrm{B}}");
		case "Epsilon":
			return new Replacement("\\mathord{\\mathrm{E}}");
		case "Zeta":
			return new Replacement("\\mathord{\\mathrm{Z}}");
		case "Eta":
			return new Replacement("\\mathord{\\mathrm{H}}");
		case "Iota":
			return new Replacement("\\mathord{\\mathrm{I}}");
		case "Kappa":
			return new Replacement("\\mathord{\\mathrm{K}}");
		case "Mu":
			return new Replacement("\\mathord{\\mathrm{M}}");
		case "Nu":
			return new Replacement("\\mathord{\\mathrm{N}}");
		case "Omicron":
			return new Replacement("\\mathord{\\mathrm{O}}");
		case "Rho":
			return new Replacement("\\mathord{\\mathrm{P}}");
		case "Tau":
			return new Replacement("\\mathord{\\mathrm{T}}");
		case "Chi":
			return new Replacement("\\mathord{\\mathrm{X}}");
		case "hdots":
			return new Replacement("\\ldots");
		case "restriction":
			return new Replacement("\\upharpoonright");
		case "celsius":
			return new Replacement("\\mathord{{}^\\circ\\mathrm{C}}");
		case "micro":
			return new Replacement("\\textmu");
		case "marker":
			return new Replacement(
					"{\\kern{0.25ex}\\rule{0.5ex}{1.2ex}\\kern{0.25ex}}");
		case "hybull":
			return new Replacement("\\rule[0.6ex]{1ex}{0.2ex}");
		case "block":
			return new Replacement("\\rule{1ex}{1.2ex}");
		case "uhblk":
			return new Replacement("\\rule[0.6ex]{1ex}{0.6ex}");
		case "lhblk":
			return new Replacement("\\rule{1ex}{0.6ex}");
		case "lVert":
			return new Replacement("\\Vert");
		case "rVert":
			return new Replacement("\\Vert");
		case "lvert":
			return new Replacement("\\vert");
		case "rvert":
			return new Replacement("\\vert");

		// XXX
		// case "copyright": return
		// new Replacement("\\textcircled{\\raisebox{0.2ex}{c}}");

		case "glj":
			return new Replacement("\\mathbin{\\rlap{>}\\!<}");
		case "gla":
			return new Replacement("\\mathbin{><}");
		case "alef":
			return new Replacement("\\aleph");
		case "alefsym":
			return new Replacement("\\aleph");
		case "And":
			return new Replacement("{\\;\\textampersand\\;}");
		case "and":
			return new Replacement("\\land");
		case "ang":
			return new Replacement("\\angle");
		case "Reals":
			return new Replacement("\\mathbb{R}");
		case "exist":
			return new Replacement("\\exists");
		case "hAar":
			return new Replacement("\\Leftrightarrow");

		case "C":
		case "Complex":
			return new Replacement("\\mathbb{C}");

		case "N":
		case "natnums":
			return new Replacement("\\mathbb{N}");

		case "Q":
			return new Replacement("\\mathbb{Q}");

		case "R":
		case "real":
		case "reals":
			return new Replacement("\\mathbb{R}");

		case "Z":
			return new Replacement("\\mathbb{Z}");
		case "Dagger":
			return new Replacement("\\ddagger");
		case "diamonds":
			return new Replacement("\\diamondsuit");
		case "clubs":
			return new Replacement("\\clubsuit");
		case "hearts":
			return new Replacement("\\heartsuit");
		case "spades":
			return new Replacement("\\spadesuit");
		case "infin":
			return new Replacement("\\infty");
		case "isin":
			return new Replacement("\\in");
		case "plusmn":
			return new Replacement("\\pm");
		case "sube":
			return new Replacement("\\subseteq");
		case "supe":
			return new Replacement("\\supseteq");
		case "sdot":
			return new Replacement("\\cdot");

		case "empty":
		case "O":
			return new Replacement("\\emptyset");

		case "sub":
			return new Replacement("\\subset");
		case "lang":
			return new Replacement("\\langle");
		case "rang":
			return new Replacement("\\rangle");
		case "bull":
			return new Replacement("\\bullet");

		case "geneuro":
		case "geneuronarrow":
		case "geneurowide":
			return new Replacement("\\texteuro");

		// TODO: check if this is useful or not
		case "jlmXML":
			return new CommandJlmXML();

		// caret for the editor
		case "jlmcursor":
			return new CommandJlmCursor();
		// for the editor
		case "jlmselection":
			return new CommandJlmSelection();

		// eg
		// \imagebasesixtyfour{40}{36}{data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAkCAIAAAB0Xu9BAAAAKUlEQVR42u3NMQEAAAwCIPuX1hbbAwVIn0QsFovFYrFYLBaLxWKx+M4AoNrQEWa6zscAAAAASUVORK5CYII=}
		case "imagebasesixtyfour":
			return new CommandImageBase64();

		case "&":
			return new Replacement("\\textampersand");
		case "%":
			return new Replacement("\\textpercent");

		case "$":
		case "dollar":
			return new Replacement("\\textdollar");
		}

		return null;

	}

	public static AtomConsumer get(final String name) {
		Command r = reusableMap.get(name);
		if (r != null) {
			return r;
		}

		Command c = getCommand(name);

		if (c instanceof Reusable) {
			reusableMap.put(name, c);
		}

		return c;
	}

	public static boolean exec(final TeXParser tp, final String name) {
		final Command c = getCommand(name);
		if (c != null) {
			tp.cancelPrevPos();
			if (c.init(tp)) {
				tp.addConsumer(c);
			}
			return true;
		}

		return false;
	}

	static Command getUnsafe(final String name) {
		return getCommand(name);
	}

	public static AtomConsumer getDollar() {
		return dollar;
	}

	public static AtomConsumer getDollarDollar() {
		return dollardollar;
	}
}
