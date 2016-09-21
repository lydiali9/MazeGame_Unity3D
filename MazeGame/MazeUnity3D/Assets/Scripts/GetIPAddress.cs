using UnityEngine;
using System.Collections;
using System.Xml;

public static class GetIPAddress
{
	public static string GetAddress()
	{
		string l_data = Resources.Load("Address").ToString();
		XmlDocument l_xmlDoc = new XmlDocument();
		l_xmlDoc.LoadXml(l_data);
		XmlNode l_node = l_xmlDoc.SelectSingleNode("Address");
		return l_node.InnerText;
	}
}
