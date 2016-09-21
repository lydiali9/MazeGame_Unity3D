#pragma strict

private static var m_score : int = 0;
private static var m_coinCount : int = 0;
private var m_time : float = 3600.0;
private static var m_lifeCount : int = 2;

function Start () {
	GameObject.Find("TextCoinCount").GetComponent(UI.Text).text = "* " + m_coinCount.ToString();
	GameObject.Find("TextScore").GetComponent(UI.Text).text = m_score.ToString();
}

function Update () {
	m_time -= Time.deltaTime * 1;
	GameObject.Find("TextTimeLabel").GetComponent(UI.Text).text = Mathf.RoundToInt(m_time).ToString();
	if (m_time < 0)
	{
		ReduceLife();
	}
}

function ApplyCoin()
{
	m_coinCount++;
	if (m_coinCount >= 100)
	{
		m_coinCount = 0;
		m_lifeCount++;
	}
	GameObject.Find("TextCoinCount").GetComponent(UI.Text).text = "* " + m_coinCount.ToString();
}

function ApplyScore(p_score : int)
{
	m_score += p_score;
	GameObject.Find("TextScore").GetComponent(UI.Text).text = m_score.ToString();
}

function ApplyLife()
{
	m_lifeCount++;
}

function ReduceLife()
{
	gameObject.SendMessage("ExitGame", SendMessageOptions.DontRequireReceiver);
}

function ExitGame()
{
	GameObject.FindGameObjectWithTag("Maze").BroadcastMessage("EndSuperMario", SendMessageOptions.DontRequireReceiver);
}

function WinSuperMario()
{
	GameObject.FindGameObjectWithTag("Maze").BroadcastMessage("WinSuperMario", SendMessageOptions.DontRequireReceiver);
}