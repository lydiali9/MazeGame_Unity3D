#pragma strict

private var m_medalCount : int;
private var m_roomCount : int;
private var m_trapCount : int;
private var m_enemyPlayerCount : int;

public var m_medalCountText : UnityEngine.UI.Text;
public var m_roomCountText : UnityEngine.UI.Text;
public var m_trapCountText : UnityEngine.UI.Text;
public var m_enemyPlayerCountText : UnityEngine.UI.Text;
public var m_message : UnityEngine.UI.Text;


function Start ()
{
	m_message.text = "";
}

function InitUI()
{
	m_medalCount = GameObject.FindGameObjectsWithTag("Medal").Length;
	m_roomCount = GameObject.FindGameObjectsWithTag("Room").Length;
	m_trapCount = GameObject.FindGameObjectsWithTag("Trap").Length;
	m_enemyPlayerCount = GameObject.FindGameObjectsWithTag("EnemyPlayer").Length;
	
	m_medalCountText.text = m_medalCount.ToString();
	m_roomCountText.text = m_roomCount.ToString();
	m_trapCountText.text = m_trapCount.ToString();
	m_enemyPlayerCountText.text = m_enemyPlayerCount.ToString();
}

function GetMedal()
{
	m_medalCount--;
	m_medalCountText.text = m_medalCount.ToString();
}

function GetRoom()
{
	m_roomCount--;
	m_roomCountText.text = m_roomCount.ToString();
}

function GetTrap()
{
	m_trapCount--;
	m_trapCountText.text = m_trapCount.ToString();
	ShowMessage("陷阱!!!");
}

function ShowMessage(p_message : String)
{
	m_message.text = p_message;
	yield WaitForSeconds(1.0);
	m_message.text = "";
}

function GetEnemyPlayer()
{
	m_enemyPlayerCount--;
	m_enemyPlayerCountText.text = m_enemyPlayerCount.ToString();
}