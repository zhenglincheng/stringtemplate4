/*
 * [The "BSD license"]
 *  Copyright (c) 2011 Terence Parr
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.stringtemplate.v4.compiler;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import java.io.File;
import java.io.FileInputStream;

/** */
public class TestNewStuff {
	public static void main(String[] args) throws Exception {
		Compiler2 c = new Compiler2();

		byte[] buffer = new byte[(int) new File(args[0]).length()];
		FileInputStream f = new FileInputStream(args[0]);
		f.read(buffer);
		String template = new String(buffer);

		STLexer lexer = new STLexer(new ANTLRStringStream(template));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		STTreeBuilder p = new STTreeBuilder(tokens);
		STTreeBuilder.template_return r = p.template();
		System.out.println(((CommonTree)r.getTree()).toStringTree());
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(r.getTree());
		nodes.setTokenStream(tokens);
		CodeGenerator gen = new CodeGenerator(nodes);
		gen.compiler = c;
		CompiledST impl = gen.root("t");
		impl.template = template;
		impl.dump();
	}
}
