{
  "name": "Create Comment Full Flow",
  "event": [
    {
      "listen": "prerequest",
      "script": {
        "exec": [
          "const main = async () => {",
          "    const api = new API(pm);",
          "    const rnd = new RandomUtils();",
          "    const category = await api.addCategory({",
          "        name: 'Концерты' + rnd.getRandomNumber()",
          "    });",
          "    const user = await api.addUser({",
          "        name: 'Иван Иванов',",
          "        email: `ivan${rnd.getRandomNumber()}@example.com`",
          "    });",
          "    const event = await api.addEvent(user.id, {",
          "        title: 'Рок-концерт',",
          "        annotation: 'Лучший концерт года!',",
          "        description: 'Приходите, будет круто!',",
          "        category: category.id,",
          "        eventDate: '2025-12-31 23:59:59',",
          "        location: {",
          "            lat: 55.751244,",
          "            lon: 37.618423",
          "        },",
          "        paid: false,",
          "        participantLimit: 0,",
          "        requestModeration: false",
          "    });",
          "    await api.publishEvent(event.id, {",
          "        stateAction: 'PUBLISH_EVENT'",
          "    });",
          "    pm.collectionVariables.set('uid', user.id);",
          "    pm.collectionVariables.set('eventId', event.id);",
          "}",
          "main();"
        ]
      }
    },
    {
      "listen": "test",
      "script": {
        "exec": [
          "pm.test('Статус ответа 201', () => {",
          "    pm.response.to.have.status(201);",
          "});",
          "pm.test('Ответ содержит корректные данные', () => {",
          "    const response = pm.response.json();",
          "    pm.expect(response).to.have.property('id');",
          "    pm.expect(response).to.have.property('text'); ",
          "    pm.expect(response).to.have.property('created');",
          "    pm.expect(response.eventId).to.equal(pm.collectionVariables.get('eventId'));",
          "});",
          "const response = pm.response.json();",
          "pm.collectionVariables.set('commentId', response.id);"
        ]
      }
    }
  ],
  "request": {
    "method": "POST", 
    "url": "{{baseUrl}}/users/{{uid}}/events/{{eventId}}/comments",
    "header": [
      {
        "key": "Content-Type",
        "value": "application/json"
      }
    ],
    "body": {
      "mode": "raw",
      "raw": {
        "text": "Отличное событие!",
        "eventId": "{{eventId}}"
      }
    }
  }
}
