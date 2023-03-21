import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { CustomMessagesService } from '../../services/custom-messages.service';

@Component({
  selector: 'app-message-popup',
  templateUrl: './message-popup.component.html',
  styleUrls: ['./message-popup.component.css']
})
export class MessagePopupComponent {

  constructor(private messageService: MessageService,
    private customMessageService: CustomMessagesService) { }

  ngOnInit() {
    this.customMessageService.onError$.subscribe(message => {
      this.messageService.add(message);
    })
  }
}
