package example;
import hydraulic.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestExample {
	
	@Test
	public void testAll(){

		// R1
		HSystem s = new HSystem();
		
		assertNotNull("Apparently getElements() is not implemented yet",s.getElements());
		assertEquals("Initially no elements are present in the system",0,s.getElements().length);
		
		// the elements of the system are defined and added
		Source src = new Source("Src");
		s.addElement(src);
		Tap r = new Tap("R");
		s.addElement(r);
		Split t = new Split("T");
		s.addElement(t);
		Sink sinkA = new Sink("sink A");
		s.addElement(sinkA);
		Sink sinkB = new Sink("sink B");
		s.addElement(sinkB);

		assertEquals("Src",src.getName());
		assertEquals("sink B",sinkB.getName());
		assertArrayEquals(new Element[] {src,r,t,sinkA,sinkB}, s.getElements());
		
		
		// elements are then connected
		src.connect(r);
		r.connect(t);
		t.connect(sinkA,0);
		t.connect(sinkB,1);
		
		assertSame("Output of src should be r",r,src.getOutput());
		assertArrayEquals("Outputs of t should be sink A and sink B",new Element[] {sinkA,sinkB},t.getOutputs());

		// imulation parameters are then defined
		src.setFlow(20);
		r.setOpen(true);
		
		// simulation starts
		PrintingObserver obs = new PrintingObserver();
		s.simulate(obs);
		assertEquals("Expected 5 notifications",5,obs.getCount());
		
		
		
		// -----------------------
		// Additional requirements
		// -----------------------
	
		// Create a multi-split and some additional sink
		Multisplit ms = new Multisplit("MS",3);
		s.addElement(ms);
		Sink sinkC = new Sink("sink C");
		s.addElement(sinkC);
		Sink sinkD = new Sink("sink D");
		s.addElement(sinkD);

		// Change the system including the multi-split
		src.connect(r);
		r.connect(ms);
		ms.connect(t,0);
		ms.connect(sinkC,1);
		ms.connect(sinkD,2);
		
		// simulation parameters are then defined
		src.setFlow(20);
		r.setOpen(true);
		ms.setProportions(.25,.35,.40);
		
		// simulation starts
		obs = new PrintingObserver();
		s.simulate(obs);
		assertTrue("Expected at least 8 notifications but received just " + obs.getCount(), 
				   obs.getCount() >= 8);
		
		// show the layout of the system
		String layout = s.layout();
		System.out.println(layout);
		assertTrue("Should start with the Src", layout.startsWith("[Src"));
		assertTrue("Should includes Sink B", layout.indexOf("sink B")>0);
		
		// delete the tap
		s.deleteElement("R");
		System.out.println(s.layout());
		assertSame("Output of src should be t",ms,src.getOutput());
		
		// simulate with check of simulation parameters against
		//	  the maximum flow rate of elements
		// WARNING: first make elements classes extend ElementExt, then uncomment the following lines
		ms.setMaxFlow(20);
		t.setMaxFlow(10);
		sinkA.setMaxFlow(10);
		sinkB.setMaxFlow(15);
		sinkC.setMaxFlow(3); // should raise error message, inFlow 8.0 but maxFlow 5.0
		sinkD.setMaxFlow(8);
		obs = new PrintingObserver();
		s.simulate(obs,true);
		assertEquals("Missing some simulation notification",7,obs.getCount());
		assertEquals("Error notification not received",1,obs.getErrorCount());
	}
}
