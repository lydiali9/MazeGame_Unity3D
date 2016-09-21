#pragma strict

function OnTriggerEnter(p_collider : Collider)
{
	p_collider.SendMessage("EnterRoom", SendMessageOptions.DontRequireReceiver);
}