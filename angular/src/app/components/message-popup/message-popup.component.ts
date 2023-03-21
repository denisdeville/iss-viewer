import { Component } from '@angular/core';
import { Message, MessageService } from 'primeng/api';
import { MapService } from 'src/app/services/map.service';
import { CustomMessagesService } from '../../services/custom-messages.service';

@Component({
  selector: 'app-message-popup',
  templateUrl: './message-popup.component.html',
  styleUrls: ['./message-popup.component.css']
})
export class MessagePopupComponent {

  constructor(private messageService: MessageService,
    private mapService: MapService,
    private customMessageService: CustomMessagesService) { }

  ngOnInit() {
    this.customMessageService.onError$.subscribe(message => {
      this.messageService.add(message);
    })
    
    this.customMessageService.onAlert$.subscribe(message => {
      this.messageService.add(message);
    })
  }

  check(message: Message) {
    this.mapService.zoomTofeature(message.data);
    this.messageService.clear(message.key);
  }
}
