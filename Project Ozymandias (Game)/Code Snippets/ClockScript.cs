using UnityEngine;
using System.Collections;
using TMPro;

public class ClockScript : MonoBehaviour
{
    public TMP_Text textTimer;
    public TMP_Text textLetters;
    public TMP_Text textDay;
    public GameObject fadeEffectGO;
    public GDTFadeEffect fadeEffect;
    public FadeUI fadeUI;

    public float timer = 0.0f;
    private int dayCounter = 1;
    private bool isTimer = true;


    private void Start()
    {
        timer = 450f;
    }

    void Update()
    {
        if (isTimer)
        {
            timer += Time.deltaTime;
            DisplayTimer();
        }

        if ((timer > 1197 && timer < 1198) || (timer > 477 && timer < 478))
        {
            Debug.Log("Fading to back");
            fadeEffectGO.SetActive(true);
            fadeEffect.callFade();
            Time.timeScale = 1.0f;
            StartCoroutine(disableFade());
            StartCoroutine(showText());
        }

        if (timer > 1440.0f)
        {
            dayCounter++;
            timer = 0.0f;
        }

        if (timer > 0.0f && timer < 720.0f)
        {
            textLetters.text = "aM";
        }
        else
        {
            textLetters.text = "pM";
        }

        if (timer < 480.0f || timer > 1200.0f)
        {
            textTimer.color = Color.red;
        }
        else
        {
            textTimer.color = Color.white;
        }

        textDay.text = "day " + dayCounter;

        if(Input.GetKey(KeyCode.Return))
        {
            Time.timeScale = 2.0f;
        }



    }

    void DisplayTimer()
    {
        int minutes = Mathf.FloorToInt(timer / 60.0f);
        int seconds = Mathf.FloorToInt(timer - minutes * 60);
        textTimer.text = string.Format("{00:00}:{1:00}", minutes, seconds);
    }

    IEnumerator disableFade()
    { 
        yield return new WaitForSeconds(10);
        fadeEffectGO.SetActive(false);
    }

    IEnumerator showText()
    {
        yield return new WaitForSeconds(1);
        fadeUI.triggerText();
        StartCoroutine(removeText());
    }

    IEnumerator removeText()
    {
        yield return new WaitForSeconds(7);
        fadeUI.disableTime();
    }

    public float getTimer()
    {
        return timer;
    }

}