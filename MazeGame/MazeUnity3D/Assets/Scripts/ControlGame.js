#pragma strict

public var SuperMario : GameObject;

function ExitGame()
{
	Application.Quit();
}

function StartSuperMario()
{
	if (!GameObject.FindGameObjectWithTag("SuperMario"))
	{
		GameObject.Instantiate(SuperMario, new Vector3(0, 0, 0), new Quaternion(0, 0, 0, 0));
	}
	GameObject.FindGameObjectWithTag("Maze").transform.position = new Vector3(100, 0, 100);
}

function EndSuperMario()
{
	if (GameObject.FindGameObjectWithTag("SuperMario"))
	{
		Destroy(GameObject.FindGameObjectWithTag("SuperMario"));
	}
	GameObject.FindGameObjectWithTag("Maze").transform.position = new Vector3(0, 0, 0);
	GameObject.FindGameObjectWithTag("MainPlayerObject").SendMessage("FinishEnter", SendMessageOptions.DontRequireReceiver);
}

function WinSuperMario()
{
	EndSuperMario();
	//TODO get reward.
}

function ExitMaze()
{
	Application.LoadLevel("Menu");
}