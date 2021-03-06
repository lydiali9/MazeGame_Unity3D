﻿#pragma strict

@script RequireComponent(CharacterController)

var m_forwardSpeed : float = 2;

private var m_thisTransform : Transform;
private var m_character : CharacterController;
private var m_movingInX : int = 0;
private var m_movingInY : int = 0;
private var m_spawn : GameObject;
private var m_scripts : GameObject;
private var m_currentRoom : GameObject;
private var m_isHited : boolean = false;
private var m_waitTime : float = 0.5;

public var m_playerControlCamera : Camera;
public var m_uiCamera : Camera;
public var m_playerCamera : Camera;
public var m_ui : GameObject;

function Start ()
{
	m_spawn = GameObject.FindGameObjectWithTag("Respawn");
	m_scripts = GameObject.FindGameObjectWithTag("Maze");
	m_thisTransform = GetComponent(Transform);
	m_character = GetComponent(CharacterController);
}

function Update ()
{
	var l_movement = new Vector3(0, 0, 0);
	l_movement += new Vector3(m_forwardSpeed * m_movingInX, 0, m_forwardSpeed * m_movingInY);
	l_movement *= Time.deltaTime;
	m_character.Move(l_movement);
	
	if (m_isHited)
	{
		m_waitTime -= Time.deltaTime;
		if (m_waitTime <= 0)
		{
			m_isHited = false;
			m_waitTime = 0.5f;
		}
	}
}

function StartMoveUp()
{
	m_movingInY = 1;
}

function StartMoveDown()
{
	m_movingInY = -1;
}

function StartMoveLeft()
{
	m_movingInX = -1;
}

function StartMoveRight()
{
	m_movingInX = 1;
}

function StopMove()
{
	m_movingInX = 0;
	m_movingInY = 0;
}

function OnControllerColliderHit (p_hit : ControllerColliderHit)
{
	if (m_isHited)
	{
		return;
	}
	
	if (p_hit.collider.gameObject.tag.Equals("GemBig"))
    {
		m_isHited = true;
		m_spawn.transform.position = p_hit.gameObject.transform.position;
		m_ui.SendMessage("GetGemBig", SendMessageOptions.DontRequireReceiver);
		m_scripts.SendMessage("GetObject", p_hit.collider.gameObject.transform.position, SendMessageOptions.DontRequireReceiver);
		Destroy(p_hit.gameObject);
		return;
    }
    if (p_hit.collider.gameObject.tag.Equals("GemMid"))
    {
		m_isHited = true;
		m_spawn.transform.position = p_hit.gameObject.transform.position;
		m_ui.SendMessage("GetGemMid", SendMessageOptions.DontRequireReceiver);
		m_scripts.SendMessage("GetObject", p_hit.collider.gameObject.transform.position, SendMessageOptions.DontRequireReceiver);
		Destroy(p_hit.gameObject);
		return;
    }
    if (p_hit.collider.gameObject.tag.Equals("GemSmall"))
    {
		m_isHited = true;
		m_spawn.transform.position = p_hit.gameObject.transform.position;
		m_ui.SendMessage("GetGemSmall", SendMessageOptions.DontRequireReceiver);
		m_scripts.SendMessage("GetObject", p_hit.collider.gameObject.transform.position, SendMessageOptions.DontRequireReceiver);
		Destroy(p_hit.gameObject);
		return;
    }
    if (p_hit.collider.gameObject.tag.Equals("Medal"))
    {
		m_isHited = true;
		m_spawn.transform.position = p_hit.gameObject.transform.position;
		m_ui.SendMessage("GetMedal", SendMessageOptions.DontRequireReceiver);
		m_scripts.SendMessage("GetObject", p_hit.collider.gameObject.transform.position, SendMessageOptions.DontRequireReceiver);
		Destroy(p_hit.gameObject);
		return;
    }
    if (p_hit.collider.gameObject.tag.Equals("Trap"))
    {
    	m_isHited = true;
    	m_ui.SendMessage("GetTrap", SendMessageOptions.DontRequireReceiver);
    	m_scripts.SendMessage("GetObject", p_hit.collider.gameObject.transform.position, SendMessageOptions.DontRequireReceiver);
		gameObject.transform.position = m_spawn.transform.position;
		Destroy(p_hit.gameObject);
		return;
    }
    if (p_hit.collider.gameObject.tag.Equals("EnemyPlayer"))
    {
    	m_isHited = true;
		//TODO Enter other player's maze.\
		
		//Destroy after back.
		m_spawn.transform.position = p_hit.gameObject.transform.position;
		m_ui.SendMessage("GetEnemyPlayer", SendMessageOptions.DontRequireReceiver);
		m_scripts.SendMessage("GetObject", p_hit.collider.gameObject.transform.position, SendMessageOptions.DontRequireReceiver);
		Destroy(p_hit.gameObject);
		return;
    }
}

function EnterRoom()
{
	if (m_isHited)
	{
		return;
	}
	m_isHited = true;
	m_uiCamera.enabled = false;
	m_playerControlCamera.enabled = false;
	m_playerCamera.enabled = false;
	m_scripts.SendMessage("StartSuperMario", SendMessageOptions.DontRequireReceiver);
}

function EnterOtherPlayer(p_playerId : int)
{
	if (m_isHited)
	{
		return;
	}
	m_isHited = true;
	m_uiCamera.enabled = false;
	m_playerControlCamera.enabled = false;
	m_playerCamera.enabled = false;
	m_scripts.SendMessage("TryEnterOtherPlayer", p_playerId, SendMessageOptions.DontRequireReceiver);
}

//TODO
//function WinRoom()
//{
//	m_spawn.transform.position = p_hit.gameObject.transform.position;
//	m_scripts.SendMessage("GetRoom", SendMessageOptions.DontRequireReceiver);
//	m_scripts.SendMessage("GetObject", p_hit.collider.gameObject.transform.position, SendMessageOptions.DontRequireReceiver);
//	Destroy(p_hit.gameObject);
//}

function FinishEnter()
{
	m_uiCamera.enabled = true;
	m_playerControlCamera.enabled = true;
	m_playerCamera.enabled = true;
}

function WrongArea()
{
	m_ui.SendMessage("ShowMessage", "玩家离开该区域，无法进入", SendMessageOptions.DontRequireReceiver);
}

function OnlineState()
{
	m_ui.SendMessage("ShowMessage", "玩家在线，无法进入", SendMessageOptions.DontRequireReceiver);
}

function FailToEnter()
{
	m_ui.SendMessage("ShowMessage", "连接异常", SendMessageOptions.DontRequireReceiver);
}

function ExitMaze()
{
	m_scripts.SendMessage("ExitMaze", SendMessageOptions.DontRequireReceiver);
}

function ExitOtherPlayer()
{
	m_scripts.SendMessage("ExitOtherPlayer", SendMessageOptions.DontRequireReceiver);
}