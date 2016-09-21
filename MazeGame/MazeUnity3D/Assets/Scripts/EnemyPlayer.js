#pragma strict

private var m_playerId : int;

function Start () {

}

function Update () {

}

function OnTriggerEnter(p_collider : Collider)
{
	p_collider.SendMessage("EnterOtherPlayer", m_playerId, SendMessageOptions.DontRequireReceiver);
}

function setPlayerId(p_playerId : int)
{
	this.m_playerId = p_playerId;
}